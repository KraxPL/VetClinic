package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Animal;
import pl.krax.vetclinic.entities.Vet;

import java.util.List;

public interface VetService {
    void save(Vet vet);

    VetDto findById(Long vetId);

    List<VetDto> findAll();

    void update(VetDto vetDto);
}
