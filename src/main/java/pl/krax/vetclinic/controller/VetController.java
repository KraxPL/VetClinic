package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.service.VetService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final VetService vetService;
    private final List<String> degrees = Arrays.asList("tech. wet.", "lek. wet.", "dr n. wet.", "dr hab. n. wet.", "prof. dr hab.");
    @GetMapping
    public String listAll(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "/vet/list";
    }
    @GetMapping("/add")
    public String addNewVetForm(Model model){
        model.addAttribute("newVet", new Vet());
        model.addAttribute("degrees", this.degrees);
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
    @GetMapping("/edit/{vetId}")
    public String editVetForm(@PathVariable Long vetId, Model model){
        VetDto vetDto = vetService.findById(vetId);
        if (vetDto != null) { //try with resources needed in the future
            model.addAttribute("vet", vetDto);
            model.addAttribute("degrees", this.degrees);
        }
        return "/vet/edit";
    }
    @PostMapping("/edit")
    public String editVet(@Valid VetDto vetDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/vet/edit";
        }
        vetService.update(vetDto);
        return "redirect:/vets";
    }
}