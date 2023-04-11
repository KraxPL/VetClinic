package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    @OneToMany
    private List<MedicalHistory> medicalHistoryList;
    private int activeAccount;
}
