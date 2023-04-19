package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krax.vetclinic.entities.VetDailySchedule;

import java.util.List;

public interface VetDailyScheduleRepository extends JpaRepository<VetDailySchedule, Long> {
    @Query("SELECT s FROM VetDailySchedule s WHERE s.vet.id = :vetId")
    List<VetDailySchedule> findAllByVetId(@Param("vetId") Long vetId);
}
