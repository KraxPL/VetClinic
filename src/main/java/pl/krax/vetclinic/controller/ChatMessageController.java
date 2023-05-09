package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.krax.vetclinic.entities.ChatMessage;
import pl.krax.vetclinic.entities.ChatRoom;
import pl.krax.vetclinic.service.ChatMessageService;
import pl.krax.vetclinic.service.ChatRoomService;
import pl.krax.vetclinic.service.VetService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final VetService vetService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{chatRoomId}")
    public void handleMessage(ChatMessage chatMessage, @DestinationVariable Long chatRoomId) {
        chatMessage.setTimestamp(LocalDateTime.now());
        Long vetId = chatRoomService.findChatRoomById(chatRoomId).getVetId();
        ChatRoom chatRoom = chatRoomService.getOrCreateChatRoom(vetId, chatMessage.getSender());
        chatMessage.setChatRoom(chatRoom);
        chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + chatRoomId, chatMessage);
    }

    @GetMapping("/viewChatRoom")
    public String viewChatRoom(@RequestParam("chatRoomId") Long chatRoomId,
                               @RequestParam("name") String username,
                               @RequestParam("vetId") String vetId, Model model) {
        List<ChatMessage> chatMessages = chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
        model.addAttribute("chatMessages", chatMessages);
        model.addAttribute("username", username);
        model.addAttribute("vetId", vetId);
        return "/chat/chat";
    }

    @GetMapping("/selectVet")
    public String chatWithVetSelection(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "/chat/chatSelection";
    }

    @GetMapping("/create-chat-room")
    public ResponseEntity<String> createChatRoom(@RequestParam("veterinarian") Long vetId,
                                                 @RequestParam("name") String name) {
        ChatRoom chatRoom = chatRoomService.getOrCreateChatRoom(vetId, name);
        Long chatRoomId = chatRoom.getId();
        String url = "/viewChatRoom?chatRoomId=" + chatRoomId + "&name=" + name + "&vetId=" + vetId;
        return ResponseEntity.ok(url);
    }
    @GetMapping("/chats/{vetId}")
    public String vetChatList(@PathVariable Long vetId, Model model){
        List<ChatRoom> chatRooms = chatRoomService.findChatRoomsByVeterinarian(vetId);
        model.addAttribute("chatRooms", chatRooms);
        return "/chat/vetChatsList";
    }
    @GetMapping("/chats/{vetId}/{id}")
    public String getChatRoom(@PathVariable Long vetId,
                              @PathVariable Long id, Model model) {
        ChatRoom chatRoom = chatRoomService.findChatRoomById(id);
        if (!Objects.equals(chatRoom.getVetId(), vetId)){
            return "redirect:/chats/" + vetId;
        }
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("vetName", vetService.findById(vetId)
                .getName());
        return "/chat/vetChat";
    }
}