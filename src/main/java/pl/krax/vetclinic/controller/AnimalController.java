package pl.krax.vetclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.vetclinic.dto.AnimalDto;
import pl.krax.vetclinic.dto.PetOwnerDto;
import pl.krax.vetclinic.mappers.PetOwnerMapper;
import pl.krax.vetclinic.repository.AnimalRepository;
import pl.krax.vetclinic.repository.MedicalHistoryRepository;
import pl.krax.vetclinic.repository.PaymentRecordRepository;
import pl.krax.vetclinic.service.AnimalService;
import pl.krax.vetclinic.service.PetOwnerService;

import java.util.List;


@Controller
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    public static final String RECORDS_ARE_ASSOCIATED_WITH_THIS_ANIMAL = "Deletion of animal not permitted: Medical records are associated with this animal";
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
        return "redirect:/owners/" + ownerId;
    }
    @PostMapping("/add")
    public String addPet(@ModelAttribute("pet") @Valid AnimalDto animalDto,
                         BindingResult bindingResult, @RequestParam Long ownerId, Model model){
        if (bindingResult.hasErrors()){
            PetOwnerDto petOwnerDto = ownerService.findById(ownerId);
            model.addAttribute("PetOwner", petOwnerDto);
            return "/animal/add";
        }
        PetOwnerDto ownerDto = ownerService.findById(ownerId);
        animalDto.setOwner(ownerMapper.fromDto(ownerDto, animalRepository, medicalHistoryRepository, paymentRecordRepository));
        animalService.save(animalDto);
        return "redirect:/owners/" + animalDto.getOwner().getId();
    }
    @GetMapping("/edit/{petId}")
    public String editPetForm(@PathVariable Long petId, Model model){
        AnimalDto animalDto = animalService.findById(petId);
        if (animalDto != null){
            model.addAttribute("pet", animalDto);
            return "/animal/edit";
        }
        return "redirect:/animals/" + petId;
    }
    @PostMapping("/edit")
    public String editPet(@ModelAttribute("pet") @Valid AnimalDto animalDto, BindingResult bindingResult,
                          Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("pet", animalDto);
            return "/animal/edit";
        }
        PetOwnerDto ownerDto = ownerService.findById(animalDto.getOwner().getId());
        animalDto.setOwner(ownerMapper.fromDto(ownerDto, animalRepository, medicalHistoryRepository, paymentRecordRepository));
        animalService.update(animalDto);
        return "redirect:/owners/" + ownerDto.getId();
    }
    @PostMapping("/delete/{petId}")
    public String deletePetOwner(@PathVariable Long petId, Model model){
        AnimalDto animalDto = animalService.findById(petId);
        if (animalDto == null){
            return "redirect:/animals/" + petId;
        }
        if ((animalDto.getMedicalHistoryIds().size()) > 0) {
            model.addAttribute("errorMessage", RECORDS_ARE_ASSOCIATED_WITH_THIS_ANIMAL);
            model.addAttribute("pet", animalDto);
            return "/animal/details";
        }
        animalService.deleteById(petId);
        return "redirect:/animals";
    }
    @PostMapping
    public String searchedAnimalList(@RequestParam(defaultValue = "name") String searchField,
                                       @RequestParam(required = false) String searchPhrase,
                                       @RequestParam(defaultValue = "50")int limit,
                                       Model model){
        List<AnimalDto> searchedAnimals = animalService.findBySearchedPhraseAndField(searchPhrase, searchField, limit);
        model.addAttribute("pets", searchedAnimals);
        return "/animal/list";
    }
}
