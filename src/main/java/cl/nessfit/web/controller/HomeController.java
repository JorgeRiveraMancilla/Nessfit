package cl.nessfit.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping ("/profile")
    public String profile(Model model) {
        User user = userServiceInterface.searchBy(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping ("/password")
    public String password(Model model) {
        User user = userServiceInterface.searchBy(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "password";
    }

    @GetMapping ("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }
}