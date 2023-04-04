package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
