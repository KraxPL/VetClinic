package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.PaymentRecord;

import java.util.List;

public interface PaymentRecordService {
    void save(PaymentRecord paymentRecord);

    PaymentRecord findById(Long paymentId);

    List<PaymentRecord> findAll();

    void update(PaymentRecord paymentRecord);

    void deleteById(Long paymentId);
}
