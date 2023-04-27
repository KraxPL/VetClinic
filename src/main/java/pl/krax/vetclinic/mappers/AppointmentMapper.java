package pl.krax.vetclinic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.krax.vetclinic.dto.AppointmentDto;
import pl.krax.vetclinic.entities.Appointment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(source = "vet.id", target = "vetId")
    @Mapping(source = "vetSchedule.id", target = "vetScheduleId")
    @Mapping(expression = "java((int) java.time.Duration.between(appointment.getStartDateTime(), appointment.getEndDateTime()).toMinutes())", target = "durationInMin")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(source = "vetId", target = "vet.id")
    @Mapping(source = "vetScheduleId", target = "vetSchedule.id")
    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDtoList(List<Appointment> appointments);

    List<Appointment> toEntityList(List<AppointmentDto> appointmentDtos);

}
