package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.service.VetDailyScheduleService;
import pl.krax.vetclinic.service.VetService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final VetDailyScheduleService scheduleService;
    private final VetService vetService;

    @GetMapping("/{userId}")
    public String displayUsersSchedule(@PathVariable Long userId,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate week,
                                       Model model){
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek;
        startOfWeek = Objects.requireNonNullElse(week, today).with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
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
        model.addAttribute("vetId", userId);
        return "/schedule/userSchedules";
    }

    @PostMapping
    public String saveSchedule(@RequestParam List<LocalDate> date, @RequestParam List<Long> id,
                               @RequestParam Long veterinarianId, @RequestParam List<LocalTime> workStartTime,
                               @RequestParam List<LocalTime> workEndTime, @RequestParam int visitTime) {
        IntStream.range(0, date.size()).forEach(i -> {
            DailyScheduleDto scheduleDto = scheduleService.findByDateAndVetId(date.get(i), veterinarianId);
            if (Objects.nonNull(scheduleDto)) {
                scheduleDto.setWorkStartTime(workStartTime.get(i));
                scheduleDto.setWorkEndTime(workEndTime.get(i));
                scheduleDto.setVisitTime(visitTime);
                scheduleService.save(scheduleDto);
            } else {
                DailyScheduleDto newSchedule = DailyScheduleDto.builder()
                        .id(id.get(i))
                        .vetId(veterinarianId)
                        .date(date.get(i))
                        .workStartTime(workStartTime.get(i))
                        .workEndTime(workEndTime.get(i))
                        .visitTime(visitTime)
                        .build();
                scheduleService.save(newSchedule);
            }
        });
        return "redirect:/schedule/" + veterinarianId;
    }

}
