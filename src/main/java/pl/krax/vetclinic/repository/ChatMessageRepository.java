package pl.krax.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krax.vetclinic.entities.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);
}
