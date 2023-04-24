package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

}
