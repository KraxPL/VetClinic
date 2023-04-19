package pl.krax.vetclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String degree;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    private List<Long> medicalHistoryIds;
    @NotEmpty
    private Set<Role> roles;
}
