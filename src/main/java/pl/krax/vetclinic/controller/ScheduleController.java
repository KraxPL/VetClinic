package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.VetDailySchedule;
import pl.krax.vetclinic.service.VetDailyScheduleService;
import pl.krax.vetclinic.service.VetService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final VetDailyScheduleService scheduleService;
    private final VetService vetService;

    @GetMapping("/{userId}")
    public String displayUsersSchedule(@PathVariable Long userId, Model model){
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        List<DailyScheduleDto> schedules = scheduleService.showAllSchedulesForVet(userId);
        Map<LocalDate, DailyScheduleDto> dayScheduleMap = schedules.stream()
                .collect(Collectors.toMap(DailyScheduleDto::getDate, Function.identity()));
        List<LocalDate> days = Stream.iterate(startOfWeek, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startOfWeek, endOfWeek.plusDays(1)))
                .collect(Collectors.toList());


        model.addAttribute("vets", vetService.findAll());
        model.addAttribute("week", startOfWeek);
        model.addAttribute("days", days);
        model.addAttribute("scheduleDto", new DailyScheduleDto());
        model.addAttribute("dayScheduleMap", dayScheduleMap);
        return "/schedule/userSchedules";
    }


//    @PostMapping
//    public String saveSchedule(@ModelAttribute("scheduleDto") DailyScheduleDto scheduleDto) {
//        VetDailySchedule schedule = scheduleService.getScheduleById(scheduleDto.getId());
//        schedule.setWorkStartTime(scheduleDto.getWorkStartTime());
//        schedule.setWorkEndTime(scheduleDto.getWorkEndTime());
//        schedule.setVisitTime(scheduleDto.getVisitTime());
//        scheduleService.saveSchedule(schedule);
//        return "redirect:/schedule/" + schedule.getVet().getId();
//    }

}
