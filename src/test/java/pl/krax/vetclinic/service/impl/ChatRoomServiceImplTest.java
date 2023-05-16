package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krax.vetclinic.entities.ChatRoom;
import pl.krax.vetclinic.repository.ChatRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ChatRoomServiceImplTest {

    private ChatRoomServiceImpl chatRoomService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chatRoomService = new ChatRoomServiceImpl(chatRoomRepository);
    }

    @Test
    public void testGetOrCreateChatRoom_ExistingChatRoom() {
        Long vetId = 1L;
        String customer = "John";

        ChatRoom existingChatRoom = new ChatRoom();
        existingChatRoom.setId(1L);
        existingChatRoom.setVetId(vetId);
        existingChatRoom.setCustomer(customer);

        when(chatRoomRepository.findByVetIdAndCustomer(vetId, customer)).thenReturn(Optional.of(existingChatRoom));

        ChatRoom resultChatRoom = chatRoomService.getOrCreateChatRoom(vetId, customer);

        assertEquals(existingChatRoom, resultChatRoom);
        verify(chatRoomRepository, times(1)).findByVetIdAndCustomer(vetId, customer);
        verify(chatRoomRepository, never()).save(any(ChatRoom.class));
    }

    @Test
    public void testGetOrCreateChatRoom_NewChatRoom() {
        Long vetId = 1L;
        String customer = "John";

        when(chatRoomRepository.findByVetIdAndCustomer(vetId, customer)).thenReturn(Optional.empty());

        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setId(1L);
        newChatRoom.setVetId(vetId);
        newChatRoom.setCustomer(customer);

        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(newChatRoom);

        ChatRoom resultChatRoom = chatRoomService.getOrCreateChatRoom(vetId, customer);

        assertEquals(newChatRoom, resultChatRoom);
        verify(chatRoomRepository, times(1)).findByVetIdAndCustomer(vetId, customer);
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }

    @Test
    public void testFindChatRoomById_ExistingChatRoom() {
        Long chatRoomId = 1L;

        ChatRoom existingChatRoom = new ChatRoom();
        existingChatRoom.setId(chatRoomId);

        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(existingChatRoom));

        ChatRoom resultChatRoom = chatRoomService.findChatRoomById(chatRoomId);

        assertEquals(existingChatRoom, resultChatRoom);
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
    }

    @Test
    public void testFindChatRoomById_NonExistingChatRoom() {
        Long chatRoomId = 1L;
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.empty());

        ChatRoom resultChatRoom = chatRoomService.findChatRoomById(chatRoomId);

        assertNull(resultChatRoom);
        verify(chatRoomRepository, times(1)).findById(chatRoomId);
    }

    @Test
    public void testFindChatRoomsByVeterinarian() {
        Long vetId = 1L;

        List<ChatRoom> expectedChatRooms = new ArrayList<>();

        when(chatRoomRepository.findChatRoomsByVetId(vetId)).thenReturn(expectedChatRooms);

        List<ChatRoom> resultChatRooms = chatRoomService.findChatRoomsByVeterinarian(vetId);

        assertEquals(expectedChatRooms, resultChatRooms);
        verify(chatRoomRepository, times(1)).findChatRoomsByVetId(vetId);
    }
}