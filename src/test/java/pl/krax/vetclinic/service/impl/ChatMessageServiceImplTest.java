package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krax.vetclinic.entities.ChatMessage;
import pl.krax.vetclinic.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatMessageServiceImplTest {

    private ChatMessageServiceImpl chatMessageService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chatMessageService = new ChatMessageServiceImpl(chatMessageRepository);
    }

    @Test
    public void testSave() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessageService.save(chatMessage);
        verify(chatMessageRepository, times(1)).save(chatMessage);
    }

    @Test
    public void testGetChatMessagesByChatRoomId() {
        Long chatRoomId = 1L;
        List<ChatMessage> expectedMessages = new ArrayList<>();

        when(chatMessageRepository.findByChatRoomId(chatRoomId)).thenReturn(expectedMessages);

        List<ChatMessage> actualMessages = chatMessageService.getChatMessagesByChatRoomId(chatRoomId);

        assertEquals(expectedMessages, actualMessages);
        verify(chatMessageRepository, times(1)).findByChatRoomId(chatRoomId);
    }

    @Test
    public void testFindByChatRoomIdOrderByTimestampAsc() {
        Long chatRoomId = 1L;
        List<ChatMessage> expectedMessages = new ArrayList<>();

        when(chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId)).thenReturn(expectedMessages);

        List<ChatMessage> actualMessages = chatMessageService.findByChatRoomIdOrderByTimestampAsc(chatRoomId);

        assertEquals(expectedMessages, actualMessages);
        verify(chatMessageRepository, times(1)).findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }
}