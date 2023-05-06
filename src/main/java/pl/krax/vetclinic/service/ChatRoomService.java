package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom getOrCreateChatRoom(Long vetId, String customer);
    ChatRoom findChatRoomById(Long id);
    List<ChatRoom> findChatRoomsByVeterinarian(String veterinarian);
}
