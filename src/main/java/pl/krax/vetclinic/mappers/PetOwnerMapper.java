package pl.krax.vetclinic.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.entities.PaymentRecord;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AnimalRepository.class, MedicalHistoryRepository.class, PaymentRecordRepository.class})
public interface PetOwnerMapper {
    PetOwnerMapper PET_OWNER_MAPPER = Mappers.getMapper(PetOwnerMapper.class);

    @Mapping(target = "animalsIds", source = "animalList", qualifiedByName = "animalsToAnimalsIds")
    @Mapping(target = "medicalHistoryIds", source = "medicalHistoryList", qualifiedByName = "historyToHistoryIds")
    @Mapping(target = "paymentRecordsIds", source = "paymentRecords", qualifiedByName = "paymentRecordsToPaymentsRecordsIds")
    PetOwnerDto toDto(PetOwner petOwner);
    @Named("animalsToAnimalsIds")
    default List<Long> animalsListToAnimalListIds(List<Animal> animals){
        return animals.stream()
                .map(Animal::getId)
                .toList();
    }
    @Named("paymentRecordsToPaymentsRecordsIds")
    default List<Long> paymentRecordsToPaymentsRecordsIds(List<PaymentRecord> payments){
        return payments.stream()
                .map(PaymentRecord::getId)
                .toList();
    }
    @Named("historyToHistoryIds")
    default List<Long> medicalHistoryToMedicalHistoryIds(List<MedicalHistory> medicalHistory){
        return medicalHistory.stream()
                .map(MedicalHistory::getId)
                .toList();
    }
    @Mapping(target = "animalList", source = "animalsIds")
    @Mapping(target = "medicalHistoryList", source = "medicalHistoryIds")
    @Mapping(target = "paymentRecords", source = "paymentRecordsIds")
    PetOwner fromDto(PetOwnerDto petOwnerDto, @Context AnimalRepository animalRepository,
                     @Context MedicalHistoryRepository medicalHistoryRepository,
                     @Context PaymentRecordRepository paymentRecordRepository);
}
