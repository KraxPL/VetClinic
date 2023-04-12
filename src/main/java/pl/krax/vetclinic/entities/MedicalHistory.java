package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTimeOfVisit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;
    private String anamnesis;
    private String vetExamination;
    private String diagnosis;
    private String usedMedication;
    private String prescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;
}
