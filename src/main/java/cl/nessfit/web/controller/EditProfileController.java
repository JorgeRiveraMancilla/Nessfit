package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class EditProfileController {
    @Autowired
    private UserServiceInterface userService;

    /**
     * Get data from edit-profile.html.
     * @param model Is the application's dynamic data structure.
     * @return access to the webpage.
     */
    @GetMapping ("/edit-profile")
    public String editProfile(Model model) {
        User actualUser = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        // The current user of the system is sent
        model.addAttribute("user", actualUser);
        model.addAttribute("firstNameMessage", "");
        model.addAttribute("lastNameMessage", "");
        model.addAttribute("emailMessage", "");
        model.addAttribute("phoneMessage", "");
        return "edit-profile";
    }

    /**
     * Receive data from edit-profile.html.
     * @param modelUser User from the html form.
     * @param model Is the application's dynamic data structure.
     * @return Return user to home page.
     */
    @PostMapping("/edit-profile")
    public String editProfile(@ModelAttribute("user") User modelUser, Model model){

        // Logged user obtained by rut
        User actualUser = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        String[] errorMessages = Validation.editProfileValidation(userService, actualUser, modelUser);

        if (errorMessages[0].equals("false")){
            model.addAttribute("firstNameMessage", errorMessages[1]);
            model.addAttribute("lastNameMessage", errorMessages[2]);
            model.addAttribute("emailMessage", errorMessages[3]);
            model.addAttribute("phoneMessage", errorMessages[4]);
            return "edit-profile";
        }

        // Update user values
        actualUser.setFirstName(modelUser.getFirstName().strip());
        actualUser.setLastName(modelUser.getLastName().strip());
        actualUser.setEmail(modelUser.getEmail().toLowerCase().strip());
        actualUser.setPhone(modelUser.getPhone().strip());
        // Save data from actualUser
        userService.save(actualUser);

        // Redirect to home.
        return "redirect:/";
    }
}