package cl.nessfit.web.controller;

import cl.nessfit.web.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/administrator")
public class ManageUserController {
    @Autowired
    private UserServiceInterface userService;

    @GetMapping ("/manage-user")
    public String manageUser(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrator/manage-user";
    }
}