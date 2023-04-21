package pl.krax.vetclinic.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleDto {
    private Long id;
    private Long vetId;
    private LocalDate date;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private int visitTime;
    private List<Long> appointmentIdsList;
    private LocalDateTime closestAvailableDateTime;
}
