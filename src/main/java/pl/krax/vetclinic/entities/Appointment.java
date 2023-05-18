package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private VetDailySchedule vetSchedule;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String visitType;
    private String message;
    private String email;
    private String phoneNumber;
    private String animalName;
    private String lastName;
    private String firstName;
    private int isActive;
}
