package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.PaymentRecord;
import pl.krax.vetclinic.entities.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
}
