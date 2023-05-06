package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    void save(ChatMessage chatMessage);
    List<ChatMessage> getChatMessagesByChatRoomId(Long chatRoomId);
}
