package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.PetOwnerMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import pl.krax.vetclinic.repository.PetOwnerRepository;
import pl.krax.vetclinic.service.PetOwnerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PetOwnerServiceImplTest {
    @Mock
    private PetOwnerRepository petOwnerRepository;

    @Mock
    private PetOwnerMapper ownerMapper;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @Mock
    private PaymentRecordRepository paymentRecordRepository;

    private PetOwnerService petOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        petOwnerService = new PetOwnerServiceImpl(petOwnerRepository, ownerMapper,
                animalRepository, medicalHistoryRepository, paymentRecordRepository);
    }

    @Test
    void save_ShouldSavePetOwnerAndReturnGeneratedId() {
        // Given
        PetOwnerDto petOwnerDto = new PetOwnerDto();
        PetOwner petOwner = new PetOwner();
        petOwner.setId(1L);

        when(ownerMapper.fromDto(eq(petOwnerDto), any(), any(), any())).thenReturn(petOwner);
        when(petOwnerRepository.save(eq(petOwner))).thenReturn(petOwner);

        // When
        Long savedOwnerId = petOwnerService.save(petOwnerDto);

        // Then
        assertNotNull(savedOwnerId);
        assertEquals(1L, savedOwnerId);
        verify(petOwnerRepository, times(1)).save(eq(petOwner));
    }

    @Test
    void findById_ShouldReturnPetOwnerDto_WhenPetOwnerExists() {
        // Given
        Long ownerId = 1L;
        PetOwner petOwner = new PetOwner();
        PetOwnerDto expectedDto = new PetOwnerDto();

        when(petOwnerRepository.findById(eq(ownerId))).thenReturn(Optional.of(petOwner));
        when(ownerMapper.toDto(eq(petOwner))).thenReturn(expectedDto);

        // When
        PetOwnerDto foundDto = petOwnerService.findById(ownerId);

        // Then
        assertNotNull(foundDto);
        assertSame(expectedDto, foundDto);
        verify(petOwnerRepository, times(1)).findById(eq(ownerId));
    }

    @Test
    void findById_ShouldThrowException_WhenPetOwnerDoesNotExist() {
        // Given
        Long ownerId = 1L;

        when(petOwnerRepository.findById(eq(ownerId))).thenReturn(Optional.empty());

        // When/Then
        assertThrows(RuntimeException.class, () -> petOwnerService.findById(ownerId));
        verify(petOwnerRepository, times(1)).findById(eq(ownerId));
    }
    @Test
    void findAll_ShouldReturnListOfPetOwnerDtos() {
        // Given
        PetOwner petOwner1 = new PetOwner();
        PetOwner petOwner2 = new PetOwner();
        List<PetOwner> owners = Arrays.asList(petOwner1, petOwner2);
        PetOwnerDto dto1 = new PetOwnerDto();
        PetOwnerDto dto2 = new PetOwnerDto();
        List<PetOwnerDto> expectedDtoList = Arrays.asList(dto1, dto2);

        when(petOwnerRepository.findAll()).thenReturn(owners);
        when(ownerMapper.toDto(any())).thenReturn(dto1, dto2);

        // When
        List<PetOwnerDto> foundDtoList = petOwnerService.findAll();

        // Then
        assertNotNull(foundDtoList);
        assertEquals(2, foundDtoList.size());
        assertSame(dto1, foundDtoList.get(0));
        assertSame(dto2, foundDtoList.get(1));
        verify(petOwnerRepository, times(1)).findAll();
    }
    @Test
    void update_ShouldSaveUpdatedPetOwner() {
        // Given
        PetOwnerDto petOwnerDto = new PetOwnerDto();
        PetOwner updatedPetOwner = new PetOwner();

        when(ownerMapper.fromDto(eq(petOwnerDto), any(), any(), any())).thenReturn(updatedPetOwner);
        when(petOwnerRepository.save(eq(updatedPetOwner))).thenReturn(updatedPetOwner);

        // When
        petOwnerService.update(petOwnerDto);

        // Then
        verify(petOwnerRepository, times(1)).save(eq(updatedPetOwner));
    }

    @Test
    void deleteById_ShouldDeletePetOwner() {
        // Given
        Long ownerId = 1L;

        // When
        petOwnerService.deleteById(ownerId);

        // Then
        verify(petOwnerRepository, times(1)).deleteById(eq(ownerId));
    }

    @Test
    void update_ShouldSaveProvidedPetOwner() {
        // Given
        PetOwner petOwner = new PetOwner();

        // When
        petOwnerService.update(petOwner);

        // Then
        verify(petOwnerRepository, times(1)).save(eq(petOwner));
    }

    @Test
    void findEntityById_ShouldReturnPetOwner_WhenPetOwnerExists() {
        // Given
        Long ownerId = 1L;
        PetOwner petOwner = new PetOwner();

        when(petOwnerRepository.findById(eq(ownerId))).thenReturn(Optional.of(petOwner));

        // When
        PetOwner foundPetOwner = petOwnerService.findEntityById(ownerId);

        // Then
        assertSame(petOwner, foundPetOwner);
        verify(petOwnerRepository, times(1)).findById(eq(ownerId));
    }

    @Test
    void findEntityById_ShouldReturnNull_WhenPetOwnerDoesNotExist() {
        // Given
        Long ownerId = 1L;

        when(petOwnerRepository.findById(eq(ownerId))).thenReturn(Optional.empty());

        // When
        PetOwner foundPetOwner = petOwnerService.findEntityById(ownerId);

        // Then
        assertNull(foundPetOwner);
        verify(petOwnerRepository, times(1)).findById(eq(ownerId));
    }

    @Test
    void findBySearchedPhraseAndField_ShouldReturnListOfPetOwnerDtos() {
        // Given
        String searchPhrase = "John";
        String searchField = "name";
        int limit = 10;
        PetOwner owner1 = new PetOwner();
        PetOwner owner2 = new PetOwner();
        List<PetOwner> owners = Arrays.asList(owner1, owner2);
        PetOwnerDto dto1 = new PetOwnerDto();
        PetOwnerDto dto2 = new PetOwnerDto();
        List<PetOwnerDto> expectedDtoList = Arrays.asList(dto1, dto2);

        when(petOwnerRepository.findByNameSearchedPhrase(eq(searchPhrase), any())).thenReturn(owners);
        when(ownerMapper.toDto(any())).thenReturn(dto1, dto2);

        // When
        List<PetOwnerDto> foundDtoList = petOwnerService.findBySearchedPhraseAndField(searchPhrase, searchField, limit);

        // Then
        assertNotNull(foundDtoList);
        assertEquals(2, foundDtoList.size());
        assertSame(dto1, foundDtoList.get(0));
        assertSame(dto2, foundDtoList.get(1));
        verify(petOwnerRepository, times(1)).findByNameSearchedPhrase(eq(searchPhrase), any());
    }

}