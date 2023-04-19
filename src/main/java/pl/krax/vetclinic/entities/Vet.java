package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull
    private String degree;
    @NotNull
    private String name;
    @Column(nullable = false, unique = true)
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL)
    private List<MedicalHistory> medicalHistoryList;
    @NotNull
    @Min(0)
    @Max(1)
    private int activeAccount;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "veterinarians_roles", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotEmpty
    private Set<Role> roles;
}

