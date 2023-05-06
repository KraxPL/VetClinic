package pl.krax.vetclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chatRooms")
@Data
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vetId;
    private String customer;
}
