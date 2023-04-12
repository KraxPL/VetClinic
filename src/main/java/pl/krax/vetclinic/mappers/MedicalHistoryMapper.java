package pl.krax.vetclinic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.entities.MedicalHistory;
@Mapper(componentModel = "spring")
public interface MedicalHistoryMapper {
    MedicalHistoryMapper MEDICAL_HISTORY_MAPPER = Mappers.getMapper(MedicalHistoryMapper.class);

    @Mapping(source = "animal.id", target = "animalId")
    @Mapping(source = "vet.id", target = "vetId")
    @Mapping(source = "petOwner.id", target = "ownerId")
    MedicalHistoryDto toDto(MedicalHistory medicalHistory);

    @Mapping(source = "animalId", target = "animal.id")
    @Mapping(source = "vetId", target = "vet.id")
    @Mapping(source = "ownerId", target = "petOwner.id")
    MedicalHistory fromDto(MedicalHistoryDto medicalHistoryDto);
}
