package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public void save(Animal animal) {
        animalRepository.save(animal);
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
    public void update(Animal animal) {
        animalRepository.save(animal);
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
}
