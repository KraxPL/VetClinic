package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.dto.VetDto;

import pl.krax.vetclinic.service.AppointmentService;
import pl.krax.vetclinic.service.VetDailyScheduleService;
import pl.krax.vetclinic.service.VetService;


import java.time.*;
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
                .startDateTime(appointmentDateTime)
                .endDateTime(appointmentDateTime.plusMinutes(visitTime))
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
    @GetMapping("/appointments/{vetId}")
    public String showAppointments(Model model, @RequestParam(required = false) LocalDate date,
                                   @PathVariable Long vetId) {
        date = forNullDateReturnNow(date);

        List<VetDto> availableVets = vetService.findAll();
        List<AppointmentDto> appointments = appointmentService.getAppointmentsByDate(date);

        LocalDate finalDate = date;
        List<AppointmentDto> appointmentsForSelectedVetAndDay = appointments.stream()
                .filter(appointment -> appointment.getVetId().equals(vetId))
                .filter(appointment -> appointment.getStartDateTime().toLocalDate().equals(finalDate))
                .sorted(Comparator.comparing(AppointmentDto::getStartDateTime))
                .toList();

        appointmentsForSelectedVetAndDay.forEach(appointment -> {
            appointment.setStartDateTime(appointment.getStartDateTime().minusHours(2));
            appointment.setEndDateTime(appointment.getEndDateTime().minusHours(2));
        });

        model.addAttribute("availableVets", availableVets);
        model.addAttribute("date", date);
        model.addAttribute("appointmentsForSelectedVetAndDay", appointmentsForSelectedVetAndDay);

        return "booking/listOfAppointmentsForSelectedDay";
    }
    @PostMapping("/appointments/reject/{appointmentId}")
    public String rejectAppointmentRequest(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes){
        AppointmentDto appointmentDto = appointmentService.findById(appointmentId);
        boolean isDeleted = appointmentService.delete(appointmentDto);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Appointment rejected successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Appointment not found!");
        }
        return "redirect:/booking/appointments/" + appointmentDto.getVetId();
    }

    @PostMapping("/appointments/accept/{appointmentId}")
    public String acceptAppointmentRequest(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes){
        AppointmentDto appointmentDto = appointmentService.findById(appointmentId);
        Long vetId = appointmentDto.getVetId();
        appointmentDto.setIsActive(1);
        appointmentService.update(appointmentDto);
        redirectAttributes.addFlashAttribute("message", "Appointment accepted successfully!");
        return "redirect:/booking/appointments/" + vetId;
    }
    @GetMapping("/appointments/create/{vetId}")
    public String newAppointmentByVetFrom(@PathVariable Long vetId, @RequestParam(required = false) LocalDate date, Model model){
        date = forNullDateReturnNow(date);
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setVetId(vetId);
        model.addAttribute("appointmentDto", appointmentDto);
        model.addAttribute("vets", vetService.findAll());
        model.addAttribute("vetId", vetId);
        model.addAttribute("defaultDate", date);
        return "/booking/createAppointmentByVet";
    }

    @PostMapping("/appointments/create")
    public String newAppointmentByVet(@ModelAttribute("appointmentDto") @Valid AppointmentDto appointmentDto,
                                      BindingResult bindingResult, Model model){

        Long vetId = appointmentDto.getVetId();
        LocalDate date = appointmentDto.getStartDateTime().toLocalDate();

        if (bindingResult.hasErrors() || !workPlanExists(date, vetId)) {
            model.addAttribute("vets", vetService.findAll());
            model.addAttribute("vetId", vetId);

            if (!workPlanExists(date, vetId)) {
                bindingResult.addError(new ObjectError("workPlanMissing",
                        "You cannot create an appointment if you did not provide a work plan for that day first!"));
            }
            return "/booking/createAppointmentByVet";
        }

        appointmentService.saveByVet(appointmentDto);
        return "redirect:/booking/appointments/" + vetId;
    }
    @GetMapping("/appointments/edit/{appointmentId}")
    public String editAppointmentForm(@PathVariable Long appointmentId, Model model){
        model.addAttribute("appointmentDto", appointmentService.findById(appointmentId));
        model.addAttribute("vets", vetService.findAll());
        return "/booking/edit";
    }
    @PostMapping("/appointments/edit")
    public String editAppointment(@Valid AppointmentDto appointmentDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("appointmentDto", appointmentService.findById(appointmentDto.getId()));
            model.addAttribute("vets", vetService.findAll());
            return "/booking/edit";
        }
        appointmentService.update(appointmentDto);
        return "redirect:/booking/appointments/" + appointmentDto.getVetId();
    }
    private LocalDate forNullDateReturnNow(LocalDate date) {
        if (date == null){
            date = LocalDate.now();
        }
        return date;
    }

    private boolean workPlanExists(LocalDate date, Long vetId) {
        DailyScheduleDto schedule = scheduleService.findByDateAndVetId(date, vetId);
        return schedule != null;
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
                LocalDateTime appointmentStartTime = appointmentDto.getStartDateTime();
                LocalDateTime appointmentEndTime = appointmentDto.getEndDateTime();
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
