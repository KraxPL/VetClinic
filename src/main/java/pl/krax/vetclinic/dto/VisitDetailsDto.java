package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDetailsDto {
    private Long id;
    private LocalDateTime dateTimeOfVisit;
    private String ownerName;
    private String animalName;
    private String diagnosis;
    private String vetName;
}
