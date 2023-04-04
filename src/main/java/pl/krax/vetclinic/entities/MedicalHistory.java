package pl.krax.vetclinic.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "medicalRecords")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTimeOfVisit;
    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;
    @ManyToOne(fetch = FetchType.LAZY)
    private Vet vet;
    private String anamnesis;
    private String vetExamination;
    private String diagnosis;
    private String usedMedication;
    private String prescription;
}
