package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("vetService", vetService);
        model.addAttribute("animalService", animalService);
        return "/visit/all";
    }
    @GetMapping("/all/{petId}")
    public String listAllForAnimalById(@PathVariable Long petId, Model model){
        model.addAttribute("visits", medicalHistoryService.findMedicalHistoriesByAnimalId(petId));
        model.addAttribute("vetService", vetService);
        model.addAttribute("animalService", animalService);
        return "/visit/all";
    }
    @GetMapping("/date")
    public String listAllVisitsBySelectedDate(@RequestParam(value = "date", required = false) LocalDate date, Model model){
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("visits", medicalHistoryService.findMedicalHistoriesByDate(date));
        model.addAttribute("vetService", vetService);
        model.addAttribute("animalService", animalService);
        return "/visit/byDate";
    }
}
