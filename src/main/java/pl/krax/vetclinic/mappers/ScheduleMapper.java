package pl.krax.vetclinic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.krax.vetclinic.dto.DailyScheduleDto;
import pl.krax.vetclinic.entities.Appointment;
import pl.krax.vetclinic.entities.VetDailySchedule;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "vet.id", target = "vetId")
    @Mapping(target = "appointmentIdsList", expression = "java(mapAppointmentIdsList(vetDailySchedule))")
    DailyScheduleDto toDto(VetDailySchedule vetDailySchedule);

    List<DailyScheduleDto> toDtos(List<VetDailySchedule> vetDailySchedules);

    @Mapping(source = "vetId", target = "vet.id")
    VetDailySchedule toEntity(DailyScheduleDto dailyScheduleDto);

    default List<Long> mapAppointmentIdsList(VetDailySchedule vetDailySchedule) {
        return vetDailySchedule.getAppointmentList().stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());
    }

}
