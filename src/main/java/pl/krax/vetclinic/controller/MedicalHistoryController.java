package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.MedicalHistoryService;
import pl.krax.vetclinic.service.VetService;

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
}
