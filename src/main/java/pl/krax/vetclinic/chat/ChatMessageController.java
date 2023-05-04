package pl.krax.vetclinic.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatMessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage get(ChatMessage chatMessage){
        return chatMessage;
    }
    @GetMapping("/chatter")
    public String chat(){
        return "/chat/chat";
    }
}
