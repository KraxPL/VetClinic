package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.PaymentRecord;

public interface PetOwnerRepository extends JpaRepository<PaymentRecord, Long> {
}
