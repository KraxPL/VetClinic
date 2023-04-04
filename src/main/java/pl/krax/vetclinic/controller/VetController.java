package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krax.vetclinic.dto.VetDto;
import pl.krax.vetclinic.entities.Vet;
import pl.krax.vetclinic.service.VetService;

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
}
