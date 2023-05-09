package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
    private Long id;
    private LocalDateTime dateTimeOfVisit;
    private String ownerName;
    private String animalName;
    private String diagnosis;
    private String anamnesis;
    private String usedMedication;
    private String prescription;
    private String vetExamination;
    private String animalBreed;
    private LocalDate dateOfBirth;
    private String animalGender;
    private String vetDegree;
    private String vetName;
}
