package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Appointment;

import pl.krax.vetclinic.entities.VetDailySchedule;
import pl.krax.vetclinic.service.AppointmentService;
import pl.krax.vetclinic.service.VetDailyScheduleService;
import pl.krax.vetclinic.service.VetService;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
public class VisitBookingController {
    private final VetDailyScheduleService scheduleService;
    private final VetService vetService;
    private final AppointmentService appointmentService;

    @GetMapping("/form")
    public String vetSelection(Model model) {
        List<VetDto> vets = vetService.findAll();
        model.addAttribute("vets", vets);
        return "booking/choosingVetAndDate";
    }

    @GetMapping
    @ResponseBody
    public Map<String, List<String>> getVetSchedule(@RequestParam Long vetId,
                                                    @RequestParam(name = "week", required = false) LocalDate dateOfWeek) {
        List<DailyScheduleDto> schedulesDtos = scheduleService.showAllSchedulesForVet(vetId);

        LocalDate monday;
        monday = Objects.requireNonNullElseGet(dateOfWeek, LocalDate::now)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        Map<String, List<String>> scheduleMap = new HashMap<>();
        for (DailyScheduleDto scheduleDto : schedulesDtos) {
            LocalDate date = scheduleDto.getDate();
            if (date.isBefore(monday) || date.isAfter(monday.plusDays(6))) {
                continue;
            }
            String dateStr = date.toString();
            List<String> availableHours = getAvailableHours(scheduleDto);
            Collections.sort(availableHours);
            scheduleMap.put(dateStr, availableHours);
        }
        return scheduleMap;
    }
    @GetMapping("/appointment/{vetId}/{appointmentDate}/{appointmentTime}")
    public String saveAppointmentForm(@PathVariable Long vetId,
                                      @PathVariable LocalDate appointmentDate,
                                      @PathVariable(name = "appointmentTime") LocalTime time,
                                      Model model){
        DailyScheduleDto scheduleDto = scheduleService.findByDateAndVetId(appointmentDate, vetId);
        Long scheduleId = scheduleDto.getId();
        int visitTime = scheduleDto.getVisitTime();
        LocalDateTime appointmentDateTime = appointmentDate.atTime(time);
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .vetId(vetId)
                .vetScheduleId(scheduleId)
                .startDateTime(appointmentDateTime.plusHours(2))
                .endDateTime(appointmentDateTime.plusMinutes(visitTime).plusHours(2))
                .build();
        model.addAttribute("appointmentDto", appointmentDto);
        return "booking/create";
    }


    @PostMapping("/appointment")
    public String saveAppointment(@ModelAttribute("appointmentDto")@Valid AppointmentDto appointmentDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "booking/create";
        }
        appointmentService.save(appointmentDto);
        return "redirect:/booking/form";
    }
    @GetMapping("/appointments")
    public String showAppointments(Model model, @RequestParam(required = false) LocalDate date) {
        List<VetDto> vets = vetService.findAll();
        if (date == null) {
            date = LocalDate.now();
        }

        int slotDurationMinutes = 30;
        int slotsPerHour = 60 / slotDurationMinutes;
        List<String> hours = new ArrayList<>();
        for (int i = 0; i < 24 * slotsPerHour; i++) {
            int hour = i / slotsPerHour;
            int minute = (i % slotsPerHour) * slotDurationMinutes;
            String hourString = String.format("%02d:%02d", hour, minute);
            hours.add(hourString);
        }

        Map<Long, Map<String, Integer>> slotsNeeded = new HashMap<>();
        Map<Long, List<AppointmentDto>> spanningAppointments = new HashMap<>();
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByDate(date);

        for (AppointmentDto appointment : appointments) {
            Long vetId = appointment.getVetId();
            LocalDateTime startDateTime = appointment.getStartDateTime();
            LocalDateTime endDateTime = appointment.getEndDateTime();

            int slotsNeededForAppointment = (int) ChronoUnit.MINUTES.between(startDateTime, endDateTime) / slotDurationMinutes;
            appointment.setColspan(slotsNeededForAppointment);

            for (int i = 0; i < slotsNeededForAppointment; i++) {
                LocalDateTime slotStartDateTime = startDateTime.plusMinutes(i * slotDurationMinutes);
                String slotStartHour = slotStartDateTime.toLocalTime().toString();
                if (!slotsNeeded.containsKey(vetId)) {
                    slotsNeeded.put(vetId, new HashMap<>());
                }
                Map<String, Integer> vetSlots = slotsNeeded.get(vetId);
                if (!vetSlots.containsKey(slotStartHour)) {
                    vetSlots.put(slotStartHour, 0);
                }
                vetSlots.put(slotStartHour, vetSlots.get(slotStartHour) + 1);

                if (i == slotsNeededForAppointment - 1 && slotsNeededForAppointment > 1) {
                    if (!spanningAppointments.containsKey(vetId)) {
                        spanningAppointments.put(vetId, new ArrayList<>());
                    }
                    List<AppointmentDto> vetSpanningAppointments = spanningAppointments.get(vetId);
                    vetSpanningAppointments.add(appointment);
                }
            }
        }

        model.addAttribute("vets", vets);
        model.addAttribute("hours", hours);
        model.addAttribute("slotsNeeded", slotsNeeded);
        model.addAttribute("date", date);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("appointments", appointments);
        model.addAttribute("spanningAppointments", spanningAppointments); // add the new map to the model attribute

        return "booking/listOfAppointmentsForSelectedDay";
    }



    private List<String> getAvailableHours(DailyScheduleDto scheduleDto) {
        List<Long> appointmentsIds = scheduleDto.getAppointmentIdsList();
        List<AppointmentDto> appointmentsDtos = appointmentService.findAppointmentsByIds(appointmentsIds);
        List<String> availableHours = new ArrayList<>();
        LocalTime currentTime = scheduleDto.getWorkStartTime();
        LocalDateTime now = LocalDateTime.now();
        while (currentTime.plusMinutes(scheduleDto.getVisitTime()).isBefore(scheduleDto.getWorkEndTime())) {
            LocalDateTime currentDateTime = LocalDateTime.of(scheduleDto.getDate(), currentTime);
            if (currentDateTime.isBefore(now)) {
                currentTime = currentTime.plusMinutes(scheduleDto.getVisitTime());
                continue;
            }
            boolean hourIsOccupied = false;
            for (AppointmentDto appointmentDto : appointmentsDtos) {
                LocalDateTime appointmentStartTime = appointmentDto.getStartDateTime().minusHours(2);
                LocalDateTime appointmentEndTime = appointmentDto.getEndDateTime().minusHours(2);
                if (scheduleDto.getDate().equals(appointmentStartTime.toLocalDate())
                        && currentTime.isBefore(appointmentEndTime.toLocalTime())
                        && appointmentStartTime.toLocalTime().isBefore(currentTime.plusMinutes(scheduleDto.getVisitTime()))) {
                    hourIsOccupied = true;
                    break;
                }
            }
            if (!hourIsOccupied) {
                availableHours.add(currentTime.toString());
            }
            currentTime = currentTime.plusMinutes(scheduleDto.getVisitTime());
        }
        return availableHours;
    }
}
