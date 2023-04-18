package pl.krax.vetclinic.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryDto {
    private Long id;
    @Past
    private LocalDateTime dateTimeOfVisit;
    private Long animalId;
    private Long vetId;
    private Long ownerId;
    @Size(max = 2500)
    private String anamnesis;
    @Size(max = 3000)
    private String vetExamination;
    @Size(max = 200, min = 3)
    private String diagnosis;
    @Size(max = 3000)
    private String usedMedication;
    @Size(max = 2500)
    private String prescription;
}
