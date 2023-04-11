package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.entities.Animal;

import java.util.List;

public interface AnimalService {
    void save(AnimalDto animaldto);

    AnimalDto findById(Long animalId);

    List<AnimalDto> findAll();

    void update(AnimalDto animalDto);

    void deleteById(Long animalId);

    List<AnimalDto> findByOwnerId(Long ownerId);
}
