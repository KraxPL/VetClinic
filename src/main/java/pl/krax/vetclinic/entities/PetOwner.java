package pl.krax.vetclinic.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "petOwners")
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
    @OneToMany
    private List<Animal> animalList;
    @OneToMany
    private List<MedicalHistory> medicalHistoryList;
    @OneToMany
    private List<PaymentRecord> paymentRecords;
}
