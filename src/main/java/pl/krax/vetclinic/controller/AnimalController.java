package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.service.AnimalService;

@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("pets", animalService.findAll());
        return "/animal/list";
    }
    @GetMapping("/{petId}")
    public String showPetDetails(@PathVariable Long petId, Model model){
        AnimalDto animalDto = animalService.findById(petId);
        model.addAttribute("pet", animalDto);
        return "/animal/details";
    }
}
