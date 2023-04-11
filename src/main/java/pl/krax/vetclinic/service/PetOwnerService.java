package pl.krax.vetclinic.service;

import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.PetOwner;

import java.util.List;

public interface PetOwnerService {
    Long save(PetOwnerDto petOwnerDto);

    PetOwnerDto findById(Long ownerId);

    List<PetOwnerDto> findAll();

    void update(PetOwnerDto petOwnerDto);

    void deleteById(Long ownerId);
}
