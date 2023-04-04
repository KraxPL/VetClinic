package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {
    void save(MedicalHistory history);

    MedicalHistory findById(Long historyId);

    List<MedicalHistory> findAll();

    void update(MedicalHistory history);

    void deleteById(Long historyId);
}
