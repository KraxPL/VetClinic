package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
}
