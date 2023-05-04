package pl.krax.vetclinic.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class ChatMessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage get(ChatMessage chatMessage){
        chatMessage.setDateTime(LocalDateTime.now());
        return chatMessage;
    }
    @GetMapping("/chatter")
    public String chat(){
        return "/chat/chat";
    }
}
