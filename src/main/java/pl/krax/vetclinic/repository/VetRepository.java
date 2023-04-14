package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.Vet;

public interface VetRepository extends JpaRepository<Vet, Long> {
    Vet findByEmail(String email);
}
