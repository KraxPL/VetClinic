package pl.krax.vetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "/main/menu";
    }
    @GetMapping("/newVisit")
    public String visitTypeSelect() {
        return "/main/newVisitSelect";
    }
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }
}
