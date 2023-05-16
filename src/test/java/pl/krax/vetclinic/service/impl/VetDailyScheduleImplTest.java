package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.entities.VetDailySchedule;
import pl.krax.vetclinic.mappers.ScheduleMapper;
import pl.krax.vetclinic.repository.VetDailyScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

class VetDailyScheduleImplTest {
    private VetDailyScheduleImpl vetDailyScheduleService;
    @Mock
    private VetDailyScheduleRepository scheduleRepository;
    @Mock
    private ScheduleMapper scheduleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vetDailyScheduleService = new VetDailyScheduleImpl(scheduleRepository, scheduleMapper);
    }

    @Test
    void save_ShouldSaveSchedule() {
        // Given
        DailyScheduleDto scheduleDto = createScheduleDto();
        VetDailySchedule scheduleEntity = createScheduleEntity();

        when(scheduleMapper.toEntity(scheduleDto)).thenReturn(scheduleEntity);

        // When
        vetDailyScheduleService.save(scheduleDto);

        // Then
        verify(scheduleMapper, times(1)).toEntity(scheduleDto);
        verify(scheduleRepository, times(1)).save(scheduleEntity);
    }

    @Test
    void showAllSchedulesForVet_ShouldReturnAllSchedulesForVet() {
        // Given
        Long vetId = 1L;
        List<VetDailySchedule> scheduleEntities = createScheduleEntities();
        List<DailyScheduleDto> expectedScheduleDtos = createScheduleDtos();

        when(scheduleRepository.findAllByVetId(vetId)).thenReturn(scheduleEntities);
        when(scheduleMapper.toDto(any(VetDailySchedule.class))).thenReturn(expectedScheduleDtos.get(0), expectedScheduleDtos.get(1), expectedScheduleDtos.get(2));

        // When
        List<DailyScheduleDto> actualScheduleDtos = vetDailyScheduleService.showAllSchedulesForVet(vetId);

        // Then
        verify(scheduleRepository, times(1)).findAllByVetId(vetId);
        verify(scheduleMapper, times(scheduleEntities.size())).toDto(any(VetDailySchedule.class));
        Assertions.assertEquals(expectedScheduleDtos, actualScheduleDtos);
    }

    @Test
    void findById_WhenScheduleExists_ShouldReturnScheduleDto() {
        // Given
        Long scheduleId = 1L;
        VetDailySchedule scheduleEntity = createScheduleEntity();
        DailyScheduleDto expectedScheduleDto = createScheduleDto();

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(scheduleEntity));
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(expectedScheduleDto);

        // When
        DailyScheduleDto actualScheduleDto = vetDailyScheduleService.findById(scheduleId);

        // Then
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(scheduleMapper, times(1)).toDto(scheduleEntity);
        Assertions.assertEquals(expectedScheduleDto, actualScheduleDto);
    }

    @Test
    void findById_WhenScheduleDoesNotExist_ShouldReturnNull() {
        // Given
        Long scheduleId = 1L;

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // When
        DailyScheduleDto actualScheduleDto = vetDailyScheduleService.findById(scheduleId);

        // Then
        verify(scheduleRepository, times(1)).findById(scheduleId);
        Assertions.assertNull(actualScheduleDto);
    }

    @Test
    void findByDateAndVetId_WhenScheduleExists_ShouldReturnScheduleDto() {
        // Given
        Long vetId = 1L;
        LocalDate date = LocalDate.now();
        VetDailySchedule scheduleEntity = createScheduleEntity();
        DailyScheduleDto expectedScheduleDto = createScheduleDto();

        when(scheduleRepository.findByDateAndVetId(date, vetId)).thenReturn(scheduleEntity);
        when(scheduleMapper.toDto(scheduleEntity)).thenReturn(expectedScheduleDto);

        // When
        DailyScheduleDto actualScheduleDto = vetDailyScheduleService.findByDateAndVetId(date, vetId);

        // Then
        verify(scheduleRepository, times(1)).findByDateAndVetId(date, vetId);
        verify(scheduleMapper, times(1)).toDto(scheduleEntity);
        Assertions.assertEquals(expectedScheduleDto, actualScheduleDto);
    }

    private DailyScheduleDto createScheduleDto() {
        DailyScheduleDto scheduleDto = new DailyScheduleDto();
        scheduleDto.setId(1L);
        scheduleDto.setVetId(1L);
        scheduleDto.setDate(LocalDate.now());

        return scheduleDto;
    }

    private VetDailySchedule createScheduleEntity() {
        VetDailySchedule scheduleEntity = new VetDailySchedule();
        scheduleEntity.setId(1L);
        scheduleEntity.setVet(new Vet());
        scheduleEntity.setDate(LocalDate.now());

        return scheduleEntity;
    }

    private List<VetDailySchedule> createScheduleEntities() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    VetDailySchedule scheduleEntity = createScheduleEntity();
                    scheduleEntity.setId((long) i);
                    return scheduleEntity;
                })
                .collect(Collectors.toList());
    }

    private List<DailyScheduleDto> createScheduleDtos() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    DailyScheduleDto scheduleDto = createScheduleDto();
                    scheduleDto.setId((long) i);
                    return scheduleDto;
                })
                .collect(Collectors.toList());
    }

}