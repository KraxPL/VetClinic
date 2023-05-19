package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.mappers.VetMapper;
import pl.krax.vetclinic.repository.VetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

class VetServiceImplTest {

    private VetServiceImpl vetService;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private VetMapper vetMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vetService = new VetServiceImpl(vetRepository, vetMapper, passwordEncoder);
    }

    @Test
    void save_ShouldEncodePasswordAndSaveVet() {
        // Given
        Vet vet = createVet();
        String encodedPassword = "password";
        when(passwordEncoder.encode(vet.getPassword())).thenReturn(encodedPassword);

        // When
        vetService.save(vet);

        // Then
        verify(passwordEncoder, times(1)).encode(vet.getPassword());
        verify(vetRepository, times(1)).save(vet);
        Assertions.assertEquals(encodedPassword, vet.getPassword());
        Assertions.assertEquals(1, vet.getActiveAccount());
    }


    @Test
    void findById_WhenVetExists_ShouldReturnVetDto() {
        // Given
        Long vetId = 1L;
        Vet vet = createVet();
        VetDto expectedDto = createVetDto();

        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        when(vetMapper.vetToDto(vet)).thenReturn(expectedDto);

        // When
        VetDto actualDto = vetService.findById(vetId);

        // Then
        verify(vetRepository, times(1)).findById(vetId);
        verify(vetMapper, times(1)).vetToDto(vet);
        Assertions.assertEquals(expectedDto, actualDto);
    }

    @Test
    void findById_WhenVetDoesNotExist_ShouldReturnNull() {
        // Given
        Long vetId = 1L;

        when(vetRepository.findById(vetId)).thenReturn(Optional.empty());

        // When
        VetDto actualDto = vetService.findById(vetId);

        // Then
        verify(vetRepository, times(1)).findById(vetId);
        Assertions.assertNull(actualDto);
    }

    @Test
    void findAll_ShouldReturnAllVetDtos() {
        // Given
        List<Vet> vets = createVets();
        List<VetDto> expectedDtos = createVetDtos();

        when(vetRepository.findAll()).thenReturn(vets);
        when(vetMapper.vetToDto(any(Vet.class))).thenReturn(expectedDtos.get(0), expectedDtos.get(1), expectedDtos.get(2));

        // When
        List<VetDto> actualDtos = vetService.findAll();

        // Then
        verify(vetRepository, times(1)).findAll();
        verify(vetMapper, times(vets.size())).vetToDto(any(Vet.class));
        Assertions.assertEquals(expectedDtos, actualDtos);
    }

    @Test
    void update_ShouldUpdateVetAndSave() {
        // Given
        VetDto vetDto = createVetDto();
        Vet editedVet = createVet();
        Vet vet = createVet();
        editedVet.setId(vetDto.getId());

        when(vetMapper.dtoToVet(vetDto)).thenReturn(editedVet);
        when(vetRepository.findById(editedVet.getId())).thenReturn(Optional.of(vet));

        // When
        vetService.update(vetDto);

        // Then
        verify(vetMapper, times(1)).dtoToVet(vetDto);
        verify(vetRepository, times(1)).findById(editedVet.getId());
        verify(vetRepository, times(1)).save(editedVet);
        Assertions.assertEquals(vet.getPassword(), editedVet.getPassword());
        Assertions.assertEquals(vet.getActiveAccount(), editedVet.getActiveAccount());
    }


    private Vet createVet() {
        Vet vet = new Vet();
        vet.setId(1L);
        vet.setName("John Doe");
        vet.setEmail("john@example.com");
        vet.setPassword("password");
        return vet;
    }

    private VetDto createVetDto() {
        VetDto vetDto = new VetDto();
        vetDto.setId(1L);
        vetDto.setName("John Doe");
        vetDto.setEmail("john@example.com");
        return vetDto;
    }

    private List<Vet> createVets() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    Vet vet = createVet();
                    vet.setId((long) i);
                    return vet;
                })
                .collect(Collectors.toList());
    }

    private List<VetDto> createVetDtos() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    VetDto vetDto = createVetDto();
                    vetDto.setId((long) i);
                    return vetDto;
                })
                .collect(Collectors.toList());
    }

    @Test
    public void testCheckPasswordRepeat() {
        String password = "password";
        boolean result = vetService.checkPasswordRepeat(password, password);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckCurrentPassword() {
        Long vetId = 1L;
        String currentPassword = "currentPassword";
        String encodedPassword = "encodedPassword";

        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setPassword(encodedPassword);

        Mockito.when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        Mockito.when(passwordEncoder.matches(currentPassword, encodedPassword)).thenReturn(true);

        boolean result = vetService.checkCurrentPassword(currentPassword, vetId);
        Assertions.assertTrue(result);
    }

    @Test
    public void testChangePassword() {
        Long vetId = 1L;
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";

        Vet vet = new Vet();
        vet.setId(vetId);
        vet.setPassword(encodedPassword);

        Mockito.when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet));
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        Mockito.when(vetRepository.save(Mockito.any(Vet.class))).thenReturn(vet);

        vetService.changePassword(newPassword, vetId);

        Mockito.verify(vetRepository).save(vet);
        Assertions.assertEquals("encodedNewPassword", vet.getPassword());
    }
}