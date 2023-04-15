package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.mappers.VetMapper;
import pl.krax.vetclinic.repository.VetRepository;
import pl.krax.vetclinic.service.VetService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {
    private final VetRepository vetRepository;
    private final VetMapper vetMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public void save(Vet vet) {
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
        vet.setActiveAccount(1);
        vet.setRoles(vet.getRoles());
        vetRepository.save(vet);
    }

    @Override
    public VetDto findById(Long vetId) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new RuntimeException("Vet not found"));
        return vetMapper.vetToDto(vet);
    }

    @Override
    public List<VetDto> findAll() {
        List<Vet> vets = vetRepository.findAll();
        return vets.stream()
                .map(vetMapper::vetToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(VetDto vetDto) {
        Vet vet = vetMapper.dtoToVet(vetDto);
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
        vetRepository.save(vet);
    }

    @Override
    public Vet findByVetEmail(String email) {
        return vetRepository.findByEmail(email);
    }
}
