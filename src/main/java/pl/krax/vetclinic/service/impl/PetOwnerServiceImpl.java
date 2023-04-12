package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.PetOwnerMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import pl.krax.vetclinic.repository.PetOwnerRepository;
import pl.krax.vetclinic.service.PetOwnerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PetOwnerServiceImpl implements PetOwnerService {
    private final PetOwnerRepository petOwnerRepository;
    private final PetOwnerMapper ownerMapper;
    private final AnimalRepository animalRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PaymentRecordRepository paymentRecordRepository;

    @Override
    public Long save(PetOwnerDto petOwnerDto) {
        PetOwner petOwner = ownerMapper.fromDto(petOwnerDto, animalRepository, medicalHistoryRepository, paymentRecordRepository);
        petOwnerRepository.save(petOwner);
        return petOwner.getId();
    }

    @Override
    public PetOwnerDto findById(Long ownerId) {
        PetOwner petOwner = petOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Pet Owner not found"));
        return ownerMapper.toDto(petOwner);
    }

    @Override
    public List<PetOwnerDto> findAll() {
        List<PetOwner> owners = petOwnerRepository.findAll();
        return owners.stream()
                .map(ownerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(PetOwnerDto petOwnerDto) {
        petOwnerRepository.save(ownerMapper.fromDto(petOwnerDto, animalRepository, medicalHistoryRepository, paymentRecordRepository));
    }

    @Override
    public void deleteById(Long ownerId) {
        petOwnerRepository.deleteById(ownerId);
    }

    @Override
    public void update(PetOwner petOwner) {
        petOwnerRepository.save(petOwner);
    }

    @Override
    public PetOwner findEntityById(Long ownerId) {
        return petOwnerRepository.findById(ownerId)
                .orElse(null);
    }
}
