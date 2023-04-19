package pl.krax.vetclinic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
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
    private List<Long> medicalHistoryIds;
    private double weight;
}
