package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.entities.Appointment;
import pl.krax.vetclinic.mappers.AppointmentMapper;
import pl.krax.vetclinic.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setIsActive(0);

        Appointment appointment = new Appointment();
        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointment);

        appointmentService.save(appointmentDto);

        verify(appointmentRepository).save(appointment);
    }

    @Test
    public void testFindAppointmentsByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentRepository.findAllAppointmentsByIds(ids)).thenReturn(appointments);
        when(appointmentMapper.toDto(any(Appointment.class))).thenReturn(new AppointmentDto());

        List<AppointmentDto> result = appointmentService.findAppointmentsByIds(ids);

        Assertions.assertEquals(ids.size(), result.size());
        verify(appointmentRepository).findAllAppointmentsByIds(ids);
        verify(appointmentMapper, Mockito.times(ids.size())).toDto(any(Appointment.class));
    }

    @Test
    public void testGetAppointmentsByDate() {
        LocalDate date = LocalDate.now();
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentRepository.getAppointmentsByDate(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(appointments);
        when(appointmentMapper.toDto(any(Appointment.class))).thenReturn(new AppointmentDto());

        List<AppointmentDto> result = appointmentService.getAppointmentsByDate(date);

        Assertions.assertEquals(appointments.size(), result.size());
        verify(appointmentRepository).getAppointmentsByDate(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(appointmentMapper, Mockito.times(appointments.size())).toDto(any(Appointment.class));
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDto(appointment)).thenReturn(new AppointmentDto());

        AppointmentDto result = appointmentService.findById(id);

        Assertions.assertNotNull(result);
        verify(appointmentRepository).findById(id);
        verify(appointmentMapper).toDto(appointment);
    }

    @Test
    public void testDelete() {
        AppointmentDto appointmentDto = new AppointmentDto();
        Appointment appointment = new Appointment();
        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointment);
        Mockito.doNothing().when(appointmentRepository).delete(appointment);

        boolean result = appointmentService.delete(appointmentDto);

        Assertions.assertTrue(result);
        verify(appointmentRepository).delete(appointment);
    }

    @Test
    public void testDelete_WhenAppointmentNotFound() {
        AppointmentDto appointmentDto = new AppointmentDto();
        Appointment appointmentEntity = new Appointment();
        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointmentEntity);
        doThrow(EmptyResultDataAccessException.class).when(appointmentRepository).delete(appointmentEntity);

        boolean result = appointmentService.delete(appointmentDto);

        verify(appointmentRepository).delete(appointmentEntity);

        Assertions.assertFalse(result);
    }
    @Test
    public void testUpdate() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setIsActive(0);
        Appointment appointmentEntity = new Appointment();
        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointmentEntity);

        appointmentService.update(appointmentDto);

        verify(appointmentMapper).toEntity(appointmentDto);
        verify(appointmentRepository).save(appointmentEntity);

    }

    @Test
    public void testSaveByVet() {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setIsActive(1);
        appointmentDto.setEndDateTime(LocalDateTime.now());

        Appointment appointmentEntity = new Appointment();
        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointmentEntity);

        appointmentService.saveByVet(appointmentDto);

        verify(appointmentMapper).toEntity(appointmentDto);
        verify(appointmentRepository).save(appointmentEntity);
    }
}