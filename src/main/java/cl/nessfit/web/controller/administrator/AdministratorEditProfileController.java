package cl.nessfit.web.controller.administrator;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping ("/administrator")
public class AdministratorEditProfileController {
    @Autowired
    private UserServiceInterface userService;

    /**
     * Get data from edit-profile.html.
     * @param model Is the application's dynamic data structure.
     * @param rut User rut to edit.
     * @return Edit page view.
     */
    @GetMapping ("/edit-profile/{rut}")
    public String editProfile(Model model, @PathVariable String rut) {
        User user = userService.searchByRut(rut);
        model.addAttribute("user", user);
        model.addAttribute("firstNameMessage", "");
        model.addAttribute("lastNameMessage", "");
        model.addAttribute("emailMessage", "");
        model.addAttribute("phoneMessage", "");
        return "administrator/edit-profile";
    }

    /**
     * Receive data from edit-profile.html.
     * @param modelUser User from the html form.
     * @param model Is the application's dynamic data structure.
     * @return Return user to home page.
     */
    @PostMapping("/edit-profile")
    public String editProfile(@Valid @ModelAttribute("user") User modelUser, Model model) {
        // Logged user obtained by rut
        User editedUser = userService.searchByRut(modelUser.getRut());
        String[] errorMessages = Validation.editProfileValidation(userService, editedUser, modelUser);

        if (errorMessages[0].equals("false")){
            model.addAttribute("firstNameMessage", errorMessages[1]);
            model.addAttribute("lastNameMessage", errorMessages[2]);
            model.addAttribute("emailMessage", errorMessages[3]);
            model.addAttribute("phoneMessage", errorMessages[4]);
            return "administrator/edit-profile";
        }

        // Update user values
        editedUser.setFirstName(modelUser.getFirstName().strip());
        editedUser.setLastName(modelUser.getLastName().strip());
        editedUser.setEmail(modelUser.getEmail().toLowerCase().strip());
        editedUser.setPhone(modelUser.getPhone().strip());
        // Save data from actualUser
        userService.save(editedUser);

        return "redirect:/administrator/manage-user";
    }
}