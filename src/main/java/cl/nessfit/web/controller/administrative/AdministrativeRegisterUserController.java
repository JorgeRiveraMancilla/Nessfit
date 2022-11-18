package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Role;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/administrative")
public class AdministrativeRegisterUserController {

    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Get and iniciate data from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return Register user view.
     */
    @GetMapping("/register-client")
    public String registerClient(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("rutMessage", "");
        model.addAttribute("firstNameMessage", "");
        model.addAttribute("lastNameMessage", "");
        model.addAttribute("emailMessage", "");
        model.addAttribute("phoneMessage", "");
        return "administrative/register-user";
    }

    /**
     * Receives data from register-user.html.
     * @param modelUser User from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return If all is ok, redirect to administrative/manage-installation.
     */
    @PostMapping("/register-client")
    public String registerClient(@ModelAttribute("user") User modelUser, Model model) {

        String[] errorMessages = Validation.registerUserValidation(userService, modelUser);

        if (errorMessages[0].equals("false")){
            model.addAttribute("rutMessage", errorMessages[1]);
            model.addAttribute("firstNameMessage", errorMessages[2]);
            model.addAttribute("lastNameMessage", errorMessages[3]);
            model.addAttribute("emailMessage", errorMessages[4]);
            model.addAttribute("phoneMessage", errorMessages[5]);
            return "administrative/register-user";
        }

        modelUser.setEmail(modelUser.getEmail().toLowerCase());
        modelUser.setStatus(1);
        modelUser.setPassword(passwordEncoder.encode(modelUser.getRut()));
        Role role = new Role();
        role.setId(3);
        modelUser.setRole(role);
        userService.save(modelUser);

        return "redirect:/administrative/manage-user";
    }
}

