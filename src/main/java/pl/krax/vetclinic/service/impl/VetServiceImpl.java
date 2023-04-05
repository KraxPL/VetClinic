package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    @Override
    public void save(Vet vet) {
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
        vetRepository.save(vetMapper.dtoToVet(vetDto));
    }
}
