package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.krax.vetclinic.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.id IN :ids")
    List<Appointment> findAllAppointmentsByIds(@Param("ids") List<Long> ids);
    @Query("SELECT a FROM Appointment a WHERE a.startDateTime BETWEEN :start AND :end")
    List<Appointment> getAppointmentsByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
