package pl.krax.vetclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.krax.vetclinic.service.VetService;


@Controller
@RequiredArgsConstructor
public class IndexController {
    private final VetService vetService;
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
    @GetMapping("/changePassword")
    public String changePasswordForm(){
        return "/vet/changePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam Long vetId, @RequestParam String currentPassword,
                                 @RequestParam String newPassword, @RequestParam String confirmPassword,
                                 Model model) {
        if (!vetService.checkPasswordRepeat(newPassword, confirmPassword) ||
                !vetService.checkCurrentPassword(currentPassword, vetId)) {
            model.addAttribute("message", "Passwords do not match or wrong password");
            return "/vet/changePassword";
        }
        vetService.changePassword(newPassword, vetId);
        return "redirect:/index";
    }
}
