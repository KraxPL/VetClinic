package pl.krax.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.entities.ChatRoom;
import pl.krax.vetclinic.repository.ChatRoomRepository;
import pl.krax.vetclinic.service.ChatRoomService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom getOrCreateChatRoom(Long vetId, String customer) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByVetIdAndCustomer(vetId, customer);
        ChatRoom chatRoom;
        if (chatRoomOptional.isPresent()) {
            chatRoom = chatRoomOptional.get();
        } else {
            chatRoom = new ChatRoom();
            chatRoom.setVetId(vetId);
            chatRoom.setCustomer(customer);
            chatRoom = chatRoomRepository.save(chatRoom);
        }
        return chatRoom;
    }

    public ChatRoom findChatRoomById(Long id) {
        return chatRoomRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<ChatRoom> findChatRoomsByVeterinarian(Long vetId) {
        return chatRoomRepository.findChatRoomsByVetId(vetId);
    }
}
