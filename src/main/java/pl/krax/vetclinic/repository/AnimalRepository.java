package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krax.vetclinic.entities.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @EntityGraph(attributePaths = {"owner", "medicalHistoryList"})
    @Query("SELECT a FROM Animal a WHERE a.owner.id = :ownerId")
    List<Animal> findByOwnerId(@Param("ownerId") Long ownerId);

    @Override
    @EntityGraph(attributePaths = {"owner", "medicalHistoryList"})
    Optional<Animal> findById(Long id);
}
