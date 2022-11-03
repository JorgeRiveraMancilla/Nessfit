package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/administrator")
public class EditUserProfileController {
    @Autowired
    UserServiceInterface userService;

    @GetMapping ("edit-profile/{rut}")
    public String editProfile(Model model, @PathVariable String rut) {
        User user = userService.searchByRut(rut);
        model.addAttribute("user", user);
        return "administrator/edit-profile";
    }
}