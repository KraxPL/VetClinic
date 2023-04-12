package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.mappers.MedicalHistoryMapper;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.service.MedicalHistoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public MedicalHistoryDto save(MedicalHistoryDto historyDto) {
        MedicalHistory history = medicalHistoryRepository.save(medicalHistoryMapper.fromDto(historyDto));
        return medicalHistoryMapper.toDto(history);
    }

    @Override
    public MedicalHistoryDto findById(Long historyId) {
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(historyId)
                .orElse(null);
        return medicalHistoryMapper.toDto(medicalHistory);
    }

    @Override
    public List<MedicalHistoryDto> findAll() {
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        return medicalHistoryList.stream()
                .map(medicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(MedicalHistoryDto historyDto) {
        medicalHistoryRepository.save(medicalHistoryMapper.fromDto(historyDto));
    }

    @Override
    public void deleteById(Long historyId) {
        medicalHistoryRepository.deleteById(historyId);
    }
}
