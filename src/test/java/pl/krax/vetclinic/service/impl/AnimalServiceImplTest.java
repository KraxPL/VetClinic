package pl.krax.vetclinic.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.AnimalMapper;
import pl.krax.vetclinic.repository.AnimalRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class AnimalServiceImplTest {

    private AnimalServiceImpl animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;
    private PetOwner petOwner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        animalService = new AnimalServiceImpl(animalRepository, animalMapper);
        petOwner = new PetOwner();
    }

    @Test
    void saveTest() {
        AnimalDto animalDto = new AnimalDto(3L, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0);
        Animal animal = new Animal(3L, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0, 0, LocalDate.now());

        when(animalMapper.fromDto(animalDto)).thenReturn(animal);

        animalService.save(animalDto);

        verify(animalMapper, times(1)).fromDto(animalDto);
        verify(animalRepository, times(1)).save(animal);

        assertEquals(animal, animalMapper.fromDto(animalDto));
    }

    @Test
    void findByIdTest() {
        Long animalId = 3L;
        Animal animal = new Animal(animalId, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0, 0, LocalDate.now());
        AnimalDto animalDto = new AnimalDto(animalId, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0);

        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(animalMapper.toDto(animal)).thenReturn(animalDto);

        AnimalDto result = animalService.findById(animalId);

        verify(animalRepository, times(1)).findById(animalId);
        verify(animalMapper, times(1)).toDto(animal);
        assertEquals(animalDto, result);
    }
    @Test
    void findAllTest() {
        List<Animal> animals = Arrays.asList(
                new Animal(1L, petOwner, "Mike", LocalDate.now(), "species1", "breed1", "male", "marks", "colour", "kind", "chip1", new ArrayList<>(), 10.0, 0, LocalDate.now()),
                new Animal(2L, petOwner, "Jack", LocalDate.now(), "species2", "breed2", "male", "marks", "colour", "kind", "chip2", new ArrayList<>(), 12.0, 0, LocalDate.now())
        );
        List<AnimalDto> expectedAnimalDtos = Arrays.asList(
                new AnimalDto(1L, petOwner, "Mike", LocalDate.now(), "species1", "breed1", "male", "marks", "colour", "kind", "chip1", new ArrayList<>(), 10.0),
                new AnimalDto(2L, petOwner, "Jack", LocalDate.now(), "species2", "breed2", "male", "marks", "colour", "kind", "chip2", new ArrayList<>(), 12.0)
        );

        when(animalRepository.findAll()).thenReturn(animals);
        when(animalMapper.toDto(animals.get(0))).thenReturn(expectedAnimalDtos.get(0));
        when(animalMapper.toDto(animals.get(1))).thenReturn(expectedAnimalDtos.get(1));

        List<AnimalDto> actualAnimalDtos = animalService.findAll();

        verify(animalRepository, times(1)).findAll();
        verify(animalMapper, times(1)).toDto(animals.get(0));
        verify(animalMapper, times(1)).toDto(animals.get(1));
        assertEquals(expectedAnimalDtos, actualAnimalDtos);
    }
@Test
void updateTest() {
    Long animalId = 3L;
    AnimalDto animalDto = new AnimalDto(animalId, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0);
    Animal animal = new Animal(animalId, petOwner, "Mike", LocalDate.now(), "species", "breed", "male", "marks", "colour", "kind", "chip", new ArrayList<>(), 10.0, 0, LocalDate.now());

    when(animalMapper.fromDto(animalDto)).thenReturn(animal);

    animalService.update(animalDto);

    verify(animalMapper, times(1)).fromDto(animalDto);
    verify(animalRepository, times(1)).save(animal);
}

    @Test
    void deleteByIdTest() {
        Long animalId = 3L;

        animalService.deleteById(animalId);

        verify(animalRepository, times(1)).deleteById(animalId);
    }
    @Test
    void findByOwnerId_shouldReturnListOfAnimalDto_whenGivenValidOwnerId() {
        // given
        List<Animal> animals = Arrays.asList(
                new Animal(1L, petOwner, "Max", LocalDate.now(), "Dog", "Labrador", "Male", "none", "Brown", "Pet", "1234", new ArrayList<>(), 10.0, 0, LocalDate.now()),
                new Animal(2L, petOwner, "Lucy", LocalDate.now(), "Cat", "Siamese", "Female", "none", "White", "Pet", "2345", new ArrayList<>(), 7.0, 0, LocalDate.now())
        );
        List<AnimalDto> animalDtos = Arrays.asList(
                new AnimalDto(1L, petOwner, "Max", LocalDate.now(), "Dog", "Labrador", "Male", "none", "Brown", "Pet", "1234", new ArrayList<>(), 10.0),
                new AnimalDto(2L, petOwner, "Lucy", LocalDate.now(), "Cat", "Siamese", "Female", "none", "White", "Pet", "2345", new ArrayList<>(), 7.0)
        );
        when(animalRepository.findByOwnerId(petOwner.getId())).thenReturn(animals);
        when(animalMapper.toDto(any(Animal.class))).thenAnswer(i -> {
            Animal animal = i.getArgument(0);
            return new AnimalDto(
                    animal.getId(),
                    animal.getOwner(),
                    animal.getName(),
                    animal.getDateOfBirth(),
                    animal.getSpecies(),
                    animal.getBreed(),
                    animal.getGender(),
                    animal.getDistinctiveMarks(),
                    animal.getColour(),
                    animal.getAnimalKind(),
                    animal.getChipNumber(),
                    animal.getMedicalHistoryList().stream()
                            .map(MedicalHistory::getId)
                            .collect(Collectors.toList()),
                    animal.getWeight()
            );
        });

        List<AnimalDto> result = animalService.findByOwnerId(petOwner.getId());

        assertEquals(animalDtos, result);
        verify(animalRepository).findByOwnerId(petOwner.getId());
        verify(animalMapper, times(2)).toDto(any(Animal.class));
    }
    @Test
    public void testFindBySearchedPhraseAndField() {
        List<Animal> animals = Arrays.asList(
                new Animal(1L, petOwner, "Max", LocalDate.now(), "Dog", "Labrador", "Male", "none", "Brown", "Pet", "1234", new ArrayList<>(), 10.0, 0, LocalDate.now()),
                new Animal(2L, petOwner, "Lucy", LocalDate.now(), "Cat", "Siamese", "Female", "none", "White", "Pet", "2345", new ArrayList<>(), 7.0, 0, LocalDate.now())
        );
        List<AnimalDto> animalDtos = Arrays.asList(
                new AnimalDto(1L, petOwner, "Max", LocalDate.now(), "Dog", "Labrador", "Male", "none", "Brown", "Pet", "1234", new ArrayList<>(), 10.0),
                new AnimalDto(2L, petOwner, "Lucy", LocalDate.now(), "Cat", "Siamese", "Female", "none", "White", "Pet", "2345", new ArrayList<>(), 7.0)
        );

        when(animalRepository.findByNameSearchedPhrase(anyString(), any(PageRequest.class))).thenReturn(animals);
        when(animalMapper.toDto(any(Animal.class))).thenAnswer(invocation -> {
            Animal animal = invocation.getArgument(0);
            return new AnimalDto(animal.getId(), animal.getOwner(), animal.getName(), animal.getDateOfBirth(),
                    animal.getSpecies(), animal.getBreed(), animal.getGender(), animal.getDistinctiveMarks(),
                    animal.getColour(), animal.getAnimalKind(), animal.getChipNumber(),
                    animal.getMedicalHistoryList().stream()
                    .map(MedicalHistory::getId)
                    .collect(Collectors.toList()),
                    animal.getWeight());
        });

        List<AnimalDto> actualAnimalDtos = animalService.findBySearchedPhraseAndField("Ma", "name", 2);

        verify(animalRepository).findByNameSearchedPhrase(eq("Ma"), any(PageRequest.class));
        verify(animalRepository, never()).findByBreedSearchedPhrase(anyString(), any(PageRequest.class));
        verify(animalRepository, never()).findByDistinctiveMarksSearchedPhrase(anyString(), any(PageRequest.class));
        verify(animalRepository, never()).findByChipNoSearchedPhrase(anyString(), any(PageRequest.class));
        assertEquals(animalDtos, actualAnimalDtos);
    }
}
