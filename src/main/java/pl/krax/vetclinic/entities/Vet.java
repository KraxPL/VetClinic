package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "veterinarians")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String degree;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL)
    private List<MedicalHistory> medicalHistoryList;
    private int activeAccount;
}

