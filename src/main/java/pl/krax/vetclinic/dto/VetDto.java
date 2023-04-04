package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VetDto {
    private Long id;
    private String degree;
    private String name;
    private String email;
    private List<Long> medicalHistoryIds;
}
