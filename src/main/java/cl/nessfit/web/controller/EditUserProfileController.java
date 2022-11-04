package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping ("/administrator")
public class EditUserProfileController {
    @Autowired
    UserServiceInterface userService;

    @GetMapping ("/edit-profile/{rut}")
    public String editProfile(Model model, @PathVariable String rut) {
        User user = userService.searchByRut(rut);
        model.addAttribute("user", user);
        return "administrator/edit-profile";
    }

    @PostMapping("/edit-profile/{rut}")
    public String editProfile(@PathVariable String rut,
                              @RequestParam("name") String firstName,
                              @RequestParam("lastname") String lastName,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              Model model) {
        User user = userService.searchByRut(rut);

        // status[] = {systemStatus, name, lastName, phone, emailExist, emailValidator}
        boolean[] status = ProfileValidation.isValidProfile(userService, user, firstName, lastName, phone, email);
        // Validate profile.
        if (!status[0]) {
            // Error messages
            model.addAttribute("msgName", status[1]);
            model.addAttribute("msgLastName", status[2]);
            model.addAttribute("msgPhone", status[3]);
            model.addAttribute("msgEmailExist", status[4]);
            model.addAttribute("msgEmailValidator", status[5]);
            model.addAttribute("user", user);
            return "administrator/edit-profile";
        }
        // Set new attributes.
        user.setFirstName(ProfileValidation.newNamesEdit(firstName));
        user.setLastName(ProfileValidation.newNamesEdit(lastName));
        user.setEmail(email);
        user.setPhone(Long.parseLong(phone));
        // Save user.
        userService.save(user);

        model.addAttribute("users", userService.getUsers());
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrator/manage-user";
    }
}