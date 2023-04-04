package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.Vet;

import java.util.List;

public interface VetService {
    void save(Vet vet);

    Vet findById(Long vetId);

    List<Vet> findAll();

    void update(Vet vet);
}
