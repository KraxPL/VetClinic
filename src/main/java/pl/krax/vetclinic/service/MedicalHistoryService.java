package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.entities.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {
    MedicalHistoryDto save(MedicalHistoryDto historyDto);

    MedicalHistoryDto findById(Long historyId);

    List<MedicalHistoryDto> findAll();

    void update(MedicalHistoryDto historyDto);

    void deleteById(Long historyId);
}
