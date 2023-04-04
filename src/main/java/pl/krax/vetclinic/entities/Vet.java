package pl.krax.vetclinic.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "veterinarians")
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String degree;
    private String name;
    private String email;
    private String password;
    @OneToMany
    private List<MedicalHistory> medicalHistoryList;
}
