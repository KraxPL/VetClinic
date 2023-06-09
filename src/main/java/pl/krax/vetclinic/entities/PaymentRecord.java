package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentInformation;
    private String paymentMethod;
    private LocalDate paymentDate;
    private BigDecimal charge;
    @OneToOne(fetch = FetchType.LAZY)
    private MedicalHistory visit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id")
    private PetOwner owner;
}

