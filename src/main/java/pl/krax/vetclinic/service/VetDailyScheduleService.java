package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.VetDailySchedule;

import java.util.List;

public interface VetDailyScheduleService {
    void save(VetDailySchedule schedule);
    List<DailyScheduleDto> showAllSchedulesForVet(Long vetId);
    void updateScheduleForVet(Long vetId, DailyScheduleDto scheduleDto);

    void deleteById(Long scheduleId);
    VetDailySchedule findById(Long scheduleId);
}
