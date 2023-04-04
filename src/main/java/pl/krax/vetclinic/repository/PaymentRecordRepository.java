package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.PaymentRecord;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
}
