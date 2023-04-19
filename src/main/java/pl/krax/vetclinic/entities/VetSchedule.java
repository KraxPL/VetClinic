package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "schedules")
@Data
public class VetSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;
    private DayOfWeek dayOfWeek;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private int visitTime;
    @OneToMany(mappedBy = "vetSchedule", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;
    private LocalDateTime closestAvailableDateTime;
}
