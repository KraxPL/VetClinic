package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.AppointmentDto;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    void save(AppointmentDto appointmentDto);
    List<AppointmentDto> findAppointmentsByIds(List<Long> ids);
    List<AppointmentDto> getAppointmentsByDate(LocalDate date);
}
