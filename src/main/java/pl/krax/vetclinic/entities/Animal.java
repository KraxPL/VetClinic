package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id")
    @NotNull
    private PetOwner owner;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @Size(max = 50)
    private String species;
    @Size(max = 50)
    private String breed;
    @Size(max = 10)
    private String gender;
    @Size(max = 100)
    private String distinctiveMarks;
    @Size(max = 100)
    private String colour;
    @Size(max = 50)
    private String animalKind;
    @Size(max = 15)
    private String chipNumber;
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<MedicalHistory> medicalHistoryList;
    private double weight;
    private int visitCount;
    @Past
    private LocalDate lastVisit;
}
