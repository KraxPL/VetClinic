package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.PetOwnerService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class PetOwnerController {
    private static final String CANNOT_DELETE_PET_OWNER_WITH_PETS_ASSIGNED = "Cannot delete pet owner with pets assigned to him/her.";
    private final PetOwnerService petOwnerService;
    private final AnimalService animalService;

    @GetMapping
    public String listAll(Model model, @RequestParam(required = false) Long ownerId) {
        model.addAttribute("petOwners", petOwnerService.findAll());
//        if (ownerId != null) {
//            List<PetDto> pets = petService.getPetsByOwnerId(ownerId);
//            model.addAttribute("pets", pets);
//        }
        return "/petOwner/list";
    }
    @GetMapping("/save")
    public String addOwnerForm(Model model){
        model.addAttribute("owner", new PetOwnerDto());
        return "/petOwner/add";
    }
    @PostMapping("/save")
    public String addOwner(@Valid PetOwnerDto petOwnerDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/petOwner/add";
        }
        Long ownerId = petOwnerService.save(petOwnerDto);
        return "redirect:/owners/" + ownerId;
    }
    @GetMapping("/{ownerId}")
    public String showOwnerDetails(@PathVariable Long ownerId, Model model){
        PetOwnerDto petOwnerDto = petOwnerService.findById(ownerId);
        model.addAttribute("owner", petOwnerDto);
        model.addAttribute("animalService", animalService);
        return "/petOwner/details";
    }
    @PostMapping("/delete/{ownerId}")
    public String deletePetOwner(@PathVariable Long ownerId, Model model){
        PetOwnerDto petOwnerDto = petOwnerService.findById(ownerId);
        if (petOwnerDto == null){
            return "redirect:/owners/" + ownerId;
        }
        List<AnimalDto> pets = animalService.findByOwnerId(ownerId);
        if (!pets.isEmpty()) {
            model.addAttribute("errorMessage", CANNOT_DELETE_PET_OWNER_WITH_PETS_ASSIGNED);
            model.addAttribute("owner", petOwnerDto);
            model.addAttribute("animalService", animalService);
            return "petOwner/details";
        }
        petOwnerService.deleteById(ownerId);
        return "redirect:/owners";
    }
    @GetMapping("/edit/{ownerId}")
    public String editPetOwnerForm(@PathVariable Long ownerId, Model model){
        PetOwnerDto petOwnerDto = petOwnerService.findById(ownerId);
        if (petOwnerDto != null){
            model.addAttribute("owner", petOwnerDto);
            return "/petOwner/edit";
        }
        return "redirect:/owners";
    }
    @PostMapping("/edit")
    public String editPetOwner(@Valid PetOwnerDto petOwnerDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/petOwner/edit";
        }
        petOwnerService.update(petOwnerDto);
        return "redirect:/owners/" + petOwnerDto.getId();
    }


    @ModelAttribute("animalIdsList")
    List<Long> animalsIds(Long ownerId){
        return animalService.findByOwnerId(ownerId)
                .stream().map(AnimalDto::getId)
                .collect(Collectors.toList());
    }
}
