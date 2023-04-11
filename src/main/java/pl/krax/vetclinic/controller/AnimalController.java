package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.entities.PetOwner;
import pl.krax.vetclinic.mappers.PetOwnerMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.PetOwnerService;

@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    private final PetOwnerService ownerService;
    private final PetOwnerMapper ownerMapper;
    private final AnimalRepository animalRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PaymentRecordRepository paymentRecordRepository;

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
    @GetMapping("/add/{ownerId}")
    public String addPetForm(@PathVariable Long ownerId, Model model){
        PetOwnerDto petOwnerDto = ownerService.findById(ownerId);
        if (petOwnerDto != null){
            model.addAttribute("PetOwner", petOwnerDto);
            model.addAttribute("pet", new AnimalDto());
            return "/animal/add";
        }
        return "redirect:/owners/save";
    }
    @PostMapping("/add")
    public String addPet(@Valid AnimalDto animalDto, BindingResult bindingResult, @RequestParam Long ownerId){
        if (bindingResult.hasErrors()){
            return "/animal/add";
        }
        PetOwnerDto ownerDto = ownerService.findById(ownerId);
        animalDto.setOwner(ownerMapper.fromDto(ownerDto, animalRepository, medicalHistoryRepository, paymentRecordRepository));
        animalService.save(animalDto);
        return "redirect:/owners/" + animalDto.getOwner().getId();
    }
}