package pl.krax.vetclinic.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.pl.NIP;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "pet_owners")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 50)
    private String name;
    @Size(max = 6)
    private String postCode;
    @Size(max = 50)
    private String city;
    @Size(max = 60)
    private String street;
    private String nip;
    @NotNull
    @Size(max = 15)
    private String phoneNumber;
    @Email
    private String email;
    @Max(100)
    @Min(0)
    private int discount;
    @Size(max = 500)
    private String extraInfo;
    private int visitCount;
    @Past
    private LocalDate lastVisit;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Animal> animalList;
    @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MedicalHistory> medicalHistoryList;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PaymentRecord> paymentRecords;
}
