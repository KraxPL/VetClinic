package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.VetDailySchedule;

import java.time.LocalDate;
import java.util.List;

public interface VetDailyScheduleService {
    void save(DailyScheduleDto scheduleDto);
    List<DailyScheduleDto> showAllSchedulesForVet(Long vetId);
    void updateScheduleForVet(Long vetId, DailyScheduleDto scheduleDto);

    void deleteById(Long scheduleId);
    VetDailySchedule findById(Long scheduleId);
    DailyScheduleDto findByDateAndVetId(LocalDate date, Long vetId);
}
