package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.MedicalHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface MedicalHistoryService {
    MedicalHistoryDto save(MedicalHistoryDto historyDto);

    MedicalHistoryDto findById(Long historyId);

    List<MedicalHistoryDto> findAll();

    void update(MedicalHistoryDto historyDto);

    void deleteById(Long historyId);
    List<MedicalHistoryDto> findMedicalHistoriesByAnimalId(Long animalId);
    List<MedicalHistoryDto> findMedicalHistoriesByDate(LocalDate date);
    List<MedicalHistoryDto> findMedicalHistoriesByOwnerId(Long ownerId);
}
