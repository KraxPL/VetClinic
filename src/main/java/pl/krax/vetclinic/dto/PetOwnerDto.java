package pl.krax.vetclinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOwnerDto {
    private Long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String name;
    @Size(min = 0, max = 6)
    private String postCode;
    @Size(min = 0, max = 50)
    private String city;
    @Size(min = 0, max = 60)
    private String street;
    private String nip;
    @NotNull
    @Size(min = 9, max = 15)
    private String phoneNumber;
    @Email
    private String email;
    @Size(min = 0, max = 500)
    private String extraInfo;
    private List<Long> animalsIds;
    private List<Long> medicalHistoryIds;
    private List<Long> paymentRecordsIds;
}
