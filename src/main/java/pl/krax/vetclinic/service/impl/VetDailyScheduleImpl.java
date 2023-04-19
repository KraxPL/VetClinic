package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.VetDailySchedule;
import pl.krax.vetclinic.mappers.ScheduleMapper;
import pl.krax.vetclinic.repository.VetDailyScheduleRepository;
import pl.krax.vetclinic.service.VetDailyScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VetDailyScheduleImpl implements VetDailyScheduleService {
    private final VetDailyScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public void save(VetDailySchedule schedule) {
    }

    @Override
    public List<DailyScheduleDto> showAllSchedulesForVet(Long vetId) {
        List<VetDailySchedule> schedulesEntitiesList = scheduleRepository.findAllByVetId(vetId);
        return schedulesEntitiesList.stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateScheduleForVet(Long vetId, DailyScheduleDto scheduleDto) {

    }

    @Override
    public void deleteById(Long scheduleId) {

    }

    @Override
    public VetDailySchedule findById(Long scheduleId) {
        return null;
    }
}
