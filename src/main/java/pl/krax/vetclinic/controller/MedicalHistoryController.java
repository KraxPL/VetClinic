package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.*;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.MedicalHistoryService;
import pl.krax.vetclinic.service.PetOwnerService;
import pl.krax.vetclinic.service.VetService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/visit")
@RequiredArgsConstructor
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;
    private final VetService vetService;
    private final AnimalService animalService;
    private final PetOwnerService petOwnerService;
    @GetMapping("/all")
    public String listAll(Model model){
        List<MedicalHistoryDto> visits = medicalHistoryService.findAll();
        return getVisitDtoIntoModelAndReturnViewFromMedicalHistoryDtoList(model, visits);
    }
    @GetMapping("/all/{petId}")
    public String listAllForAnimalById(@PathVariable Long petId, Model model){
        List<MedicalHistoryDto> visits = medicalHistoryService.findMedicalHistoriesByAnimalId(petId);
        return getVisitDtoIntoModelAndReturnViewFromMedicalHistoryDtoList(model, visits);
    }

    @GetMapping("/all/owner/{ownerId}")
    public String listAllForOwnerById(@PathVariable Long ownerId, Model model) {
        List<MedicalHistoryDto> visits = medicalHistoryService.findMedicalHistoriesByOwnerId(ownerId);
        return getVisitDtoIntoModelAndReturnViewFromMedicalHistoryDtoList(model, visits);
    }
    @GetMapping("/date")
    public String listAllVisitsBySelectedDate(@RequestParam(value = "date", required = false) LocalDate date,
                                              Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<MedicalHistoryDto> medicalHistories = medicalHistoryService.findMedicalHistoriesByDate(date);
        List<VisitDetailsDto> visits = new ArrayList<>();
        for (MedicalHistoryDto medicalHistoryDto : medicalHistories) {
            AnimalDto animalDto = animalService.findById(medicalHistoryDto.getAnimalId());
            VetDto vetDto = vetService.findById(medicalHistoryDto.getVetId());
            String ownerName = animalDto.getOwner().getName();
            String animalName = animalDto.getName();
            String vetName = vetDto.getDegree() + " " + vetDto.getName();
            visits.add(new VisitDetailsDto(medicalHistoryDto.getId(),
                    medicalHistoryDto.getDateTimeOfVisit(),
                    ownerName,
                    animalName,
                    medicalHistoryDto.getDiagnosis(),
                    vetName));
        }
        model.addAttribute("visits", visits);
        return "/visit/byDate";
    }


    @GetMapping("/{visitId}")
    public String showVisitDetails(@PathVariable Long visitId, Model model){
        MedicalHistoryDto historyDto = medicalHistoryService.findById(visitId);
        Long vetId = historyDto.getVetId();
        Long animalId = historyDto.getAnimalId();
        Long petOwnerId = historyDto.getOwnerId();

        model.addAttribute("visit", historyDto);
        model.addAttribute("vet", vetService.findById(vetId));
        model.addAttribute("animal", animalService.findById(animalId));
        model.addAttribute("petOwner", petOwnerService.findById(petOwnerId));
        return "/visit/details";
    }
    @GetMapping("/new/{petId}")
    public String addNewVisitForm(@PathVariable Long petId, Model model){
        model.addAttribute("petId", petId);
        model.addAttribute("visit", new MedicalHistoryDto());
        ownerIdFromPetIdIntoModel(model, petId);
        return "/visit/new";
    }
    @PostMapping("/new")
    public String addNewVisitForm(@ModelAttribute("visit") @Valid MedicalHistoryDto historyDto,
                                  BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            Long petId = historyDto.getAnimalId();
            model.addAttribute("petId", petId);
            ownerIdFromPetIdIntoModel(model, petId);
            return "/visit/new";
        }
        medicalHistoryService.save(historyDto);
        return "redirect:/visit/date";
    }

    @GetMapping("/edit/{visitId}")
    public String editVisitForm(@PathVariable Long visitId, Model model){
        MedicalHistoryDto historyDto = medicalHistoryService.findById(visitId);
        model.addAttribute("visit", historyDto);
        model.addAttribute("petId", historyDto.getAnimalId());
        return "/visit/edit";
    }
    @PostMapping("/edit")
    public String editVisit(@ModelAttribute("visit") @Valid MedicalHistoryDto historyDto,
                            BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("petId", historyDto.getAnimalId());
            return "/visit/edit";
        }
        medicalHistoryService.update(historyDto);
        return "redirect:/visit/date";
    }
    private void ownerIdFromPetIdIntoModel(Model model, Long petId) {
        model.addAttribute("ownerId", animalService.findById(petId).getOwner().getId());
    }

    private void vetAndAnimalServicesIntoModel(Model model) {
        model.addAttribute("vetService", vetService);
        model.addAttribute("animalService", animalService);
    }
    private String getVisitDtoIntoModelAndReturnViewFromMedicalHistoryDtoList(Model model, List<MedicalHistoryDto> visits) {
        List<VisitDto> visitsDto = new ArrayList<>();

        for (MedicalHistoryDto visit : visits) {
            VisitDto visitDto = VisitDto.builder()
                    .id(visit.getId())
                    .dateTimeOfVisit(visit.getDateTimeOfVisit())
                    .animalName(animalService.findById(visit.getAnimalId()).getName())
                    .animalBreed(animalService.findById(visit.getAnimalId()).getBreed())
                    .dateOfBirth(animalService.findById(visit.getAnimalId()).getDateOfBirth())
                    .animalGender(animalService.findById(visit.getAnimalId()).getGender())
                    .vetDegree(vetService.findById(visit.getVetId()).getDegree())
                    .vetName(vetService.findById(visit.getVetId()).getName())
                    .anamnesis(visit.getAnamnesis())
                    .vetExamination(visit.getVetExamination())
                    .diagnosis(visit.getDiagnosis())
                    .usedMedication(visit.getUsedMedication())
                    .prescription(visit.getPrescription())
                    .build();
            visitsDto.add(visitDto);
        }

        model.addAttribute("visits", visitsDto);
        return "/visit/all";
    }
}
