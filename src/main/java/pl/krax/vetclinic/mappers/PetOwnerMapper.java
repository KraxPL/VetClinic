package pl.krax.vetclinic.mappers;

import org.mapstruct.*;
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
    @Mapping(target = "animalList", ignore = true)
    @Mapping(target = "medicalHistoryList", ignore = true)
    @Mapping(target = "paymentRecords", ignore = true)
    PetOwner fromDto(PetOwnerDto petOwnerDto, @Context AnimalRepository animalRepository,
                     @Context MedicalHistoryRepository medicalHistoryRepository,
                     @Context PaymentRecordRepository paymentRecordRepository);

    @AfterMapping
    default void mapAnimalListIdsToAnimals(@MappingTarget PetOwner petOwner, PetOwnerDto petOwnerDto,
                                           @Context AnimalRepository animalRepository) {
        if (petOwnerDto.getAnimalsIds() != null) {
            List<Animal> animals = animalRepository.findAllById(petOwnerDto.getAnimalsIds());
            petOwner.setAnimalList(animals);
        }
    }

    @AfterMapping
    default void mapHistoryIdsToMedicalHistoryList(@MappingTarget PetOwner petOwner, PetOwnerDto petOwnerDto,
                                                   @Context MedicalHistoryRepository medicalHistoryRepository) {
        if (petOwnerDto.getMedicalHistoryIds() != null) {
            List<MedicalHistory> medicalHistory = medicalHistoryRepository.findAllById(petOwnerDto.getMedicalHistoryIds());
            petOwner.setMedicalHistoryList(medicalHistory);
        }
    }

    @AfterMapping
    default void mapPaymentRecordsIdsToPaymentRecords(@MappingTarget PetOwner petOwner, PetOwnerDto petOwnerDto,
                                                      @Context PaymentRecordRepository paymentRecordRepository) {
        if (petOwnerDto.getPaymentRecordsIds() != null) {
            List<PaymentRecord> paymentRecords = paymentRecordRepository.findAllById(petOwnerDto.getPaymentRecordsIds());
            petOwner.setPaymentRecords(paymentRecords);
        }
    }
}
