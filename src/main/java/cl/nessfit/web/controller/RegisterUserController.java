package cl.nessfit.web.controller;

import cl.nessfit.web.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class RegisterUserController {
    @Autowired
    UserServiceInterface userService;

    @GetMapping("/register-client")
    public String registerClient(Model model) {
        model.addAttribute("client", true);
        return "administrator/register-user";
    }

    @GetMapping("/register-administrative")
    public String registerAdministrative(Model model) {
        model.addAttribute("client", false);
        return "administrator/register-user";
    }
}