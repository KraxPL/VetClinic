package pl.krax.vetclinic.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "petOwners")
@Data
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String postCode;
    private String city;
    private String street;
    private String nip;
    private String phoneNumber;
    private String email;
    private int discount;
    private String extraInfo;
    private int visitCount;
    private LocalDate lastVisit;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Animal> animalList;
    @OneToMany
    private List<MedicalHistory> medicalHistoryList;
    @OneToMany
    private List<PaymentRecord> paymentRecords;
}
