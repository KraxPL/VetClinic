package pl.krax.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private Long id;
    private Long vetId;
    private Long vetScheduleId;
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
    private int durationInMin;
}
