package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.service.VetService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final VetService vetService;
    @GetMapping
    public String listAll(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "/vet/list";
    }
    @GetMapping("/add")
    public String addNewVetForm(Model model){
        model.addAttribute("newVet", new Vet());
        List<String> degrees = Arrays.asList("tech. wet.", "lek. wet.", "dr n. wet.", "dr hab. n. wet.", "prof. dr hab.");
        model.addAttribute("degrees", degrees);
        return "/vet/add";
    }
    @PostMapping("/add")
    public String addNewVet(@Valid Vet vet, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/vet/add";
        }
        vetService.save(vet);
        return "redirect:/vets";
    }
}
