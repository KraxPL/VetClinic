package pl.krax.vetclinic.dto;


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
    private LocalDateTime dateTimeOfVisit;
    private Long animalId;
    private Long vetId;
    private String anamnesis;
    private String vetExamination;
    private String diagnosis;
    private String usedMedication;
    private String prescription;
}
