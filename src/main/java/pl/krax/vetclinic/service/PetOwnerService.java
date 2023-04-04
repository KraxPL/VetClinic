package pl.krax.vetclinic.service;

import pl.krax.vetclinic.entities.PetOwner;

import java.util.List;

public interface PetOwnerService {
    void save(PetOwner petOwner);

    PetOwner findById(Long ownerId);

    List<PetOwner> findAll();

    void update(PetOwner petOwner);

    void deleteById(Long ownerId);
}
