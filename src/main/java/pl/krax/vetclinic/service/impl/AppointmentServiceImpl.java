package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.entities.Appointment;
import pl.krax.vetclinic.mappers.AppointmentMapper;
import pl.krax.vetclinic.repository.AppointmentRepository;
import pl.krax.vetclinic.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public void save(AppointmentDto appointmentDto) {
        appointmentDto.setIsActive(0);
        appointmentRepository.save(appointmentMapper.toEntity(appointmentDto));
    }

    @Override
    public List<AppointmentDto> findAppointmentsByIds(List<Long> ids) {
        List<Appointment> appointments = appointmentRepository.findAllAppointmentsByIds(ids);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsByDate(date.atStartOfDay(), date.atTime(23, 59, 59 ,999));
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDto findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElse(null);
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public boolean delete(AppointmentDto appointmentDto) {
        try {
            appointmentRepository.delete(appointmentMapper.toEntity(appointmentDto));
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void update(AppointmentDto appointmentDto) {
        appointmentDto.setIsActive(1);
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointmentRepository.save(appointment);
    }

    @Override
    public void saveByVet(AppointmentDto appointmentDto) {
        appointmentDto.setIsActive(1);
        appointmentDto.setStartDateTime(appointmentDto.getStartDateTime());
        appointmentDto.setEndDateTime(appointmentDto.getEndDateTime().minusSeconds(1));
        appointmentRepository.save(appointmentMapper.toEntity(appointmentDto));
    }
}
