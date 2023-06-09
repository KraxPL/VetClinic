package pl.krax.vetclinic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.entities.Vet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VetMapper {
    VetMapper VET_MAPPER = Mappers.getMapper(VetMapper.class);

    @Mapping(target = "medicalHistoryIds", source = "medicalHistoryList", qualifiedByName = "historyToHistoryIds")
    VetDto vetToDto(Vet vet);

    @Named("historyToHistoryIds")
    default List<Long> medicalHistoryToMedicalHistoryIds(List<MedicalHistory> medicalHistory) {
        if (medicalHistory == null) {
            return Collections.emptyList();
        }
        return medicalHistory.stream()
                .map(MedicalHistory::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "medicalHistoryList", ignore = true)
    Vet dtoToVet(VetDto vetDto);
}
