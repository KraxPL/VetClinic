package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krax.vetclinic.entities.MedicalHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    @EntityGraph(attributePaths = {"animal", "vet"})
    @Query("SELECT h FROM MedicalHistory h WHERE h.animal.id = :animalId")
    List<MedicalHistory> findMedicalHistoriesByAnimalId(@Param("animalId")Long animalId);
    @Query("SELECT h FROM MedicalHistory h WHERE h.dateTimeOfVisit BETWEEN :dateTimeStart AND :dateTimeEnd")
    List<MedicalHistory> findMedicalHistoriesByDate(@Param("dateTimeStart") LocalDateTime dateTimeStart,
                                                    @Param("dateTimeEnd") LocalDateTime dateTimeEnd);
    @Query("SELECT h FROM MedicalHistory h WHERE h.animal.id = :animalId")
    List<MedicalHistory> findMedicalHistoriesByIdList(@Param("animalId")Long animalId);
}
