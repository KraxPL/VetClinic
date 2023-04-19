package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.mappers.AnimalMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.service.AnimalService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    @Override
    public void save(AnimalDto animalDto) {
        animalRepository.save(animalMapper.fromDto(animalDto));
    }

    @Override
    public AnimalDto findById(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElse(null);
        if (animal != null) {
            return animalMapper.toDto(animal);
        }
        return new AnimalDto();
    }

    @Override
    public List<AnimalDto> findAll() {
        List<Animal> animals = animalRepository.findAll();
        return animals.stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(AnimalDto animalDto) {
        animalRepository.save(animalMapper.fromDto(animalDto));
    }

    @Override
    public void deleteById(Long animalId) {
        animalRepository.deleteById(animalId);
    }

    @Override
    public List<AnimalDto> findByOwnerId(Long ownerId) {
        List<Animal> animals = animalRepository.findByOwnerId(ownerId);
        return animals.stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findBySearchedPhraseAndField(String searchPhrase, String searchField, int limit) {
        List<Animal> foundResults = switch (searchField) {
            case "name" -> animalRepository.findByNameSearchedPhrase(searchPhrase, PageRequest.of(0, limit));
            case "breed" -> animalRepository.findByBreedSearchedPhrase(searchPhrase, PageRequest.of(0, limit));
            case "distinctiveMarks" -> animalRepository.findByDistinctiveMarksSearchedPhrase(searchPhrase, PageRequest.of(0, limit));
            case "chipNo" -> animalRepository.findByChipNoSearchedPhrase(searchPhrase, PageRequest.of(0, limit));
            default -> throw new IllegalArgumentException("Invalid search field: " + searchField);
        };
        return foundResults.stream()
                .map(animalMapper::toDto)
                .collect(Collectors.toList());
    }
}
