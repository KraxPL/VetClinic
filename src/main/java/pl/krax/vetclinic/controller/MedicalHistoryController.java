package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.MedicalHistoryDto;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.MedicalHistoryService;
import pl.krax.vetclinic.service.VetService;

import java.time.LocalDate;

@Controller
@RequestMapping("/visit")
@RequiredArgsConstructor
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;
    private final VetService vetService;
    private final AnimalService animalService;
    @GetMapping("/all")
    public String listAll(Model model){
        model.addAttribute("visits", medicalHistoryService.findAll());
        vetAndAnimalServicesIntoModel(model);
        return "/visit/all";
    }
    @GetMapping("/all/{petId}")
    public String listAllForAnimalById(@PathVariable Long petId, Model model){
        model.addAttribute("visits", medicalHistoryService.findMedicalHistoriesByAnimalId(petId));
        vetAndAnimalServicesIntoModel(model);
        return "/visit/all";
    }
    @GetMapping("/date")
    public String listAllVisitsBySelectedDate(@RequestParam(value = "date", required = false) LocalDate date,
                                              Model model){
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("visits", medicalHistoryService.findMedicalHistoriesByDate(date));
        vetAndAnimalServicesIntoModel(model);
        return "/visit/byDate";
    }
    @GetMapping("/{visitId}")
    public String showVisitDetails(@PathVariable Long visitId, Model model){
        model.addAttribute("visit", medicalHistoryService.findById(visitId));
        vetAndAnimalServicesIntoModel(model);
        return "/visit/details";
    }
    @GetMapping("/new/{petId}")
    public String addNewVisitForm(@PathVariable Long petId, Model model){
        model.addAttribute("petId", petId);
        model.addAttribute("visit", new MedicalHistoryDto());
        model.addAttribute("animalService", animalService);
        return "/visit/new";
    }
    @PostMapping("/new")
    public String addNewVisitForm(@Valid MedicalHistoryDto historyDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
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
    public String editVisit(@Valid MedicalHistoryDto historyDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/visit/edit";
        }
        medicalHistoryService.update(historyDto);
        return "redirect:/visit/date";
    }

    private void vetAndAnimalServicesIntoModel(Model model) {
        model.addAttribute("vetService", vetService);
        model.addAttribute("animalService", animalService);
    }
}
