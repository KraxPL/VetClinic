package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL)
    private List<MedicalHistory> medicalHistoryList;
    private int activeAccount;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "veterinarians_roles", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}

