package pl.krax.vetclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    String loginForm(){
        return "login";
    }
    @PostMapping
    String login(){

        return "/main/menu";
    }
    @GetMapping("/logout")
    String logout(){
        return "redirect:/log-out";
    }
}
