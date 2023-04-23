package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Appointment;

import pl.krax.vetclinic.service.AppointmentService;
import pl.krax.vetclinic.service.VetDailyScheduleService;
import pl.krax.vetclinic.service.VetService;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (dateOfWeek == null) {
            monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else {
            monday = dateOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }

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



    private List<String> getAvailableHours(DailyScheduleDto scheduleDto) {
        List<Long> appointmentsIds = scheduleDto.getAppointmentIdsList();
        List<AppointmentDto> appointmentsDtos = appointmentService.findAppointmentsByIds(appointmentsIds);
        List<String> availableHours = new ArrayList<>();
        LocalTime currentTime = scheduleDto.getWorkStartTime();
        LocalDateTime now = LocalDateTime.now().minusHours(2);
        while (currentTime.plusMinutes(scheduleDto.getVisitTime()).isBefore(scheduleDto.getWorkEndTime())) {
            LocalDateTime currentDateTime = LocalDateTime.of(scheduleDto.getDate(), currentTime).minusHours(2);
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


    @PostMapping("/appointment")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        // Save appointment to database
        return "redirect:/";
    }
}
