package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.krax.vetclinic.entities.Role;

import java.util.List;
import java.util.Set;

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
    private Set<Role> roles;
}
