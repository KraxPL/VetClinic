package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private PetOwner owner;
    private String name;
    private LocalDate dateOfBirth;
    private String species;
    private String breed;
    private String gender;
    private String distinctiveMarks;
    private String colour;
    private String animalKind;
    private String chipNumber;
    @OneToMany
    private List<MedicalHistory> medicalHistoryList;
    private double weight;
    private int visitCount;
    private LocalDate lastVisit;
}
