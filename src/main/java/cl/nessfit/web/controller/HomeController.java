package cl.nessfit.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;

@Controller
public class HomeController {
    @Autowired
    UserServiceInterface userServiceInterface;

    @GetMapping ({"/home", "/", ""})
    public String home() {
        return "home";
    }

    @GetMapping ("/register-user")
    public String registerUser(Model model) {
        return "register-user";
    }
}