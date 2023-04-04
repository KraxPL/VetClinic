package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.Animal;

import java.util.List;

public interface AnimalService {
    void save(Animal animal);

    Animal findById(Long animalId);

    List<Animal> findAll();

    void update(Animal animal);

    void deleteById(Long animalId);
}
