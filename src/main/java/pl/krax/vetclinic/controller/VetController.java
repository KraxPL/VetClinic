package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Role;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.repository.RoleRepository;
import pl.krax.vetclinic.service.VetService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    public static final String THIS_EMAIL_IS_ALREADY_USED = "This email is already used!";
    private final VetService vetService;
    private final RoleRepository roleRepository;
    private final List<String> degrees = Arrays.asList("tech. wet.", "lek. wet.", "dr n. wet.", "dr hab. n. wet.", "prof. dr hab.");
    @GetMapping
    public String listAll(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "/vet/list";
    }
    @GetMapping("/add")
    public String addNewVetForm(Model model){
        model.addAttribute("newVet", new Vet());
        degreesAndRolesInModel(model);
        return "/vet/add";
    }

    @PostMapping("/add")
    public String addNewVet(@ModelAttribute("newVet") @Valid Vet vet,
                            BindingResult bindingResult, Model model){
        VetDto duplicatedVet = vetService.findVetDtoByEmail(vet.getEmail());
        if (bindingResult.hasErrors() || duplicatedVet != null){
            degreesAndRolesInModel(model);
            model.addAttribute("duplicatedMail", THIS_EMAIL_IS_ALREADY_USED);
            return "/vet/add";
        }
        vetService.save(vet);
        return "redirect:/vets";
    }


    @GetMapping("/edit/{vetId}")
    public String editVetForm(@PathVariable Long vetId, Model model){
        VetDto vetDto = vetService.findById(vetId);
        if (vetDto != null) {
            model.addAttribute("vet", vetDto);
            degreesAndRolesInModel(model);
        }
        return "/vet/edit";
    }
    @PostMapping("/edit")
    public String editVet(@ModelAttribute("vet") @Valid VetDto vetDto, BindingResult bindingResult, Model model){
        VetDto duplicatedVet = vetService.findVetDtoByEmail(vetDto.getEmail());
        if (bindingResult.hasErrors() || (duplicatedVet != null && !duplicatedVet.getId().equals(vetDto.getId()))){
            model.addAttribute("duplicatedMail", THIS_EMAIL_IS_ALREADY_USED);
            degreesAndRolesInModel(model);
            return "/vet/edit";
        }
        vetService.update(vetDto);
        return "redirect:/vets";
    }
    private void degreesAndRolesInModel(Model model) {
        model.addAttribute("degrees", this.degrees);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roleList", roles);
    }


}
