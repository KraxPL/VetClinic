package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.repository.PetOwnerRepository;
import pl.krax.vetclinic.service.PetOwnerService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetOwnerServiceImpl implements PetOwnerService {
    private final PetOwnerRepository petOwnerRepository;

    @Override
    public void save(PetOwner petOwner) {
        petOwnerRepository.save(petOwner);
    }

    @Override
    public PetOwner findById(Long ownerId) {
        PetOwner petOwner = petOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Pet Owner not found"));
        return petOwner;
    }

    @Override
    public List<PetOwner> findAll() {
        return petOwnerRepository.findAll();
    }

    @Override
    public void update(PetOwner petOwner) {
        petOwnerRepository.save(petOwner);
    }

    @Override
    public void deleteById(Long ownerId) {
        petOwnerRepository.deleteById(ownerId);
    }
}
