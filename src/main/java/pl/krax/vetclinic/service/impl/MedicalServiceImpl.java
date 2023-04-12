package pl.krax.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.MedicalHistory;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.MedicalHistoryMapper;
import pl.krax.vetclinic.mappers.PetOwnerMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.MedicalHistoryService;
import pl.krax.vetclinic.service.PetOwnerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;
    private final PetOwnerService petOwnerService;
    private final AnimalService animalService;

    @Override
    public MedicalHistoryDto save(MedicalHistoryDto historyDto) {
        LocalDateTime time = LocalDateTime.now();
        historyDto.setDateTimeOfVisit(time);
        MedicalHistory history = medicalHistoryRepository.save(medicalHistoryMapper.fromDto(historyDto));
        AnimalDto animalDto = animalService.findById(history.getAnimal().getId());
        PetOwner owner = petOwnerService.findEntityById(animalDto.getOwner().getId());
        owner.setLastVisit(time.toLocalDate());
        owner.setVisitCount(owner.getVisitCount() + 1);
        petOwnerService.update(owner);
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
        return getHistoryDtos(medicalHistoryList);
    }

    @Override
    public void update(MedicalHistoryDto historyDto) {
        medicalHistoryRepository.save(medicalHistoryMapper.fromDto(historyDto));
    }

    @Override
    public void deleteById(Long historyId) {
        medicalHistoryRepository.deleteById(historyId);
    }

    @Override
    public List<MedicalHistoryDto> findMedicalHistoriesByAnimalId(Long animalId) {
        List<MedicalHistory> historyList = medicalHistoryRepository.findMedicalHistoriesByAnimalId(animalId);
        return getHistoryDtos(historyList);
    }
    public List<MedicalHistoryDto> findMedicalHistoriesByDate(LocalDate date){
        LocalDateTime dateTimeStart = date.atStartOfDay();
        LocalDateTime dateTimeEnd = date.atTime(23, 59, 59, 99);
        List<MedicalHistory> historyList = medicalHistoryRepository.findMedicalHistoriesByDate(dateTimeStart, dateTimeEnd);
        return getHistoryDtos(historyList);
    }

    private List<MedicalHistoryDto> getHistoryDtos(List<MedicalHistory> historyList) {
        return historyList.stream()
                .map(medicalHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
