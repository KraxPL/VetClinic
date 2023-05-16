package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.MedicalHistoryMapper;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.PetOwnerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class MedicalServiceImplTest {

    private MedicalServiceImpl medicalService;

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;
    @Mock
    private MedicalHistoryMapper medicalHistoryMapper;
    @Mock
    private PetOwnerService petOwnerService;
    @Mock
    private AnimalService animalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        medicalService = new MedicalServiceImpl(
                medicalHistoryRepository,
                medicalHistoryMapper,
                petOwnerService,
                animalService
        );
    }

    @Test
    public void testSave() {
        LocalDateTime currentTime = LocalDateTime.now();
        Long animalId = 1L;
        Long petOwnerId = 1L;
        PetOwner petOwner = new PetOwner(petOwnerId, "John Doe", null, null, null, null, null, null, 0, null, 0, null, null, null, null);

        MedicalHistoryDto historyDto = new MedicalHistoryDto();
        historyDto.setDateTimeOfVisit(currentTime);

        MedicalHistory history = new MedicalHistory();
        history.setDateTimeOfVisit(currentTime);
        history.setId(1L);

        AnimalDto animalDto = new AnimalDto(1L, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0);
        animalDto.setOwner(petOwner);
        Animal animal = new Animal(1L, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0, 0, LocalDate.now());
        history.setAnimal(animal);

        history.setPetOwner(petOwner);

        when(medicalHistoryMapper.fromDto(historyDto)).thenReturn(history);
        when(medicalHistoryRepository.save(history)).thenReturn(history);
        when(animalService.findById(animalId)).thenReturn(animalDto);
        when(petOwnerService.findEntityById(petOwnerId)).thenReturn(petOwner);
        when(medicalHistoryMapper.toDto(history)).thenReturn(historyDto);

        MedicalHistoryDto result = medicalService.save(historyDto);

        assertEquals(historyDto, result);
        verify(medicalHistoryRepository, times(1)).save(history);
        verify(animalService, times(1)).findById(animalId);
        verify(petOwnerService, times(1)).findEntityById(petOwnerId);
        verify(petOwnerService, times(1)).update(petOwner);
    }
    @Test
    public void testFindById_ExistingHistory() {
        Long historyId = 1L;
        MedicalHistory history = new MedicalHistory();
        history.setId(historyId);
        MedicalHistoryDto expectedDto = new MedicalHistoryDto();
        expectedDto.setId(historyId);

        when(medicalHistoryRepository.findById(historyId)).thenReturn(Optional.of(history));
        when(medicalHistoryMapper.toDto(history)).thenReturn(expectedDto);

        MedicalHistoryDto result = medicalService.findById(historyId);

        assertEquals(expectedDto, result);
        verify(medicalHistoryRepository, times(1)).findById(historyId);
    }

    @Test
    public void testFindById_NonExistingHistory() {
        Long historyId = 1L;

        when(medicalHistoryRepository.findById(historyId)).thenReturn(Optional.empty());

        MedicalHistoryDto result = medicalService.findById(historyId);

        assertNull(result);
        verify(medicalHistoryRepository, times(1)).findById(historyId);
    }

    @Test
    public void testFindAll() {
        List<MedicalHistory> medicalHistoryList = new ArrayList<>();
        MedicalHistory history1 = new MedicalHistory();
        history1.setId(1L);
        MedicalHistory history2 = new MedicalHistory();
        history2.setId(2L);
        medicalHistoryList.add(history1);
        medicalHistoryList.add(history2);
        MedicalHistoryDto expectedDto1 = new MedicalHistoryDto();
        expectedDto1.setId(1L);
        MedicalHistoryDto expectedDto2 = new MedicalHistoryDto();
        expectedDto2.setId(2L);
        List<MedicalHistoryDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(expectedDto1);
        expectedDtos.add(expectedDto2);

        when(medicalHistoryRepository.findAll()).thenReturn(medicalHistoryList);
        when(medicalHistoryMapper.toDto(history1)).thenReturn(expectedDto1);
        when(medicalHistoryMapper.toDto(history2)).thenReturn(expectedDto2);

        List<MedicalHistoryDto> result = medicalService.findAll();

        assertEquals(expectedDtos, result);
        verify(medicalHistoryRepository, times(1)).findAll();
    }
    @Test
    public void testUpdate() {
        MedicalHistoryDto historyDto = new MedicalHistoryDto();

        MedicalHistory history = new MedicalHistory();

        when(medicalHistoryMapper.fromDto(historyDto)).thenReturn(history);
        when(medicalHistoryRepository.save(history)).thenReturn(history);

        medicalService.update(historyDto);

        verify(medicalHistoryRepository, times(1)).save(history);
    }
    @Test
    public void testDeleteById() {
        Long historyId = 1L;

        medicalService.deleteById(historyId);

        verify(medicalHistoryRepository, times(1)).deleteById(historyId);
    }
    @Test
    public void testFindMedicalHistoriesByAnimalId() {
        Long animalId = 1L;

        List<MedicalHistory> historyList = new ArrayList<>();

        when(medicalHistoryRepository.findMedicalHistoriesByAnimalId(animalId)).thenReturn(historyList);
        when(medicalHistoryMapper.toDto(any())).thenAnswer(invocation -> {
            invocation.getArgument(0);

            return new MedicalHistoryDto();
        });

        List<MedicalHistoryDto> result = medicalService.findMedicalHistoriesByAnimalId(animalId);

        assertEquals(historyList.size(), result.size());
    }
    @Test
    public void testFindMedicalHistoriesByDate() {
        LocalDate date = LocalDate.now();

        LocalDateTime dateTimeStart = date.atStartOfDay();
        LocalDateTime dateTimeEnd = date.atTime(23, 59, 59, 99);

        List<MedicalHistory> historyList = new ArrayList<>();

        when(medicalHistoryRepository.findMedicalHistoriesByDate(dateTimeStart, dateTimeEnd)).thenReturn(historyList);
        when(medicalHistoryMapper.toDto(any())).thenAnswer(invocation -> {
            invocation.getArgument(0);
            return new MedicalHistoryDto();
        });

        List<MedicalHistoryDto> result = medicalService.findMedicalHistoriesByDate(date);

        assertEquals(historyList.size(), result.size());
    }
    @Test
    public void testFindMedicalHistoriesByOwnerId() {
        Long ownerId = 1L;

        List<MedicalHistory> historyList = new ArrayList<>();

        when(medicalHistoryRepository.findMedicalHistoriesByOwnerId(ownerId)).thenReturn(historyList);
        when(medicalHistoryMapper.toDto(any())).thenAnswer(invocation -> {
            invocation.getArgument(0);

            return new MedicalHistoryDto();
        });
        List<MedicalHistoryDto> result = medicalService.findMedicalHistoriesByOwnerId(ownerId);

        assertEquals(historyList.size(), result.size());
    }
}
