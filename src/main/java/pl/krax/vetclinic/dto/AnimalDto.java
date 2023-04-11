package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krax.vetclinic.entities.PetOwner;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private Long id;
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
    private List<Long> medicalHistoryIds;
    private double weight;
}
