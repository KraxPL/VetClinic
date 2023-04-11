package pl.krax.vetclinic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.MedicalHistory;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnimalMapper {
    @Mapping(target = "medicalHistoryIds", source = "medicalHistoryList", qualifiedByName = "medicalHistoryListToMedicalHistoryIds")
    AnimalDto toDto(Animal animal);
    Animal fromDto(AnimalDto animalDto);

    @Named("medicalHistoryListToMedicalHistoryIds")
    @Mapping(target = "medicalHistoryList", source = "medicalHistoryIds")
    default List<Long> mapMedicalHistoryToId(List<MedicalHistory> medicalHistoryList) {
        return medicalHistoryList.stream()
                .map(MedicalHistory::getId)
                .collect(Collectors.toList());
    }

}
