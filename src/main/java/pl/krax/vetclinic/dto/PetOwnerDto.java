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
public class PetOwnerDto {
    private Long id;
    private String name;
    private String postCode;
    private String city;
    private String street;
    private String nip;
    private String phoneNumber;
    private String email;
    private String extraInfo;
    private List<Long> animalsIds;
    private List<Long> medicalHistoryIds;
    private List<Long> paymentRecordsIds;
}
