package cl.nessfit.web.controller.administrator;

import cl.nessfit.web.model.Role;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/administrator")
public class AdministratorRegisterUserController {
    @Autowired
    UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //region REGISTER CLIENT

    /**
     * Get and iniciate data from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return Register user view.
     */
    @GetMapping("/register-client")
    public String registerClient(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("client", true);
        model.addAttribute("rutMessage", "");
        model.addAttribute("firstNameMessage", "");
        model.addAttribute("lastNameMessage", "");
        model.addAttribute("emailMessage", "");
        model.addAttribute("phoneMessage", "");
        return "administrator/register-user";
    }

    /**
     * Receives data from register-user.html.
     * @param modelUser User from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return If all is ok, redirect to administrator/manage-installation.
     */
    @PostMapping("/register-client")
    public String registerClient(@ModelAttribute("user") User modelUser, Model model) {

        String[] errorMessages = Validation.registerUserValidation(userService, modelUser);

        if (errorMessages[0].equals("false")){
            model.addAttribute("rutMessage", errorMessages[1]);
            model.addAttribute("client", true);
            model.addAttribute("firstNameMessage", errorMessages[2]);
            model.addAttribute("lastNameMessage", errorMessages[3]);
            model.addAttribute("emailMessage", errorMessages[4]);
            model.addAttribute("phoneMessage", errorMessages[5]);
            return "administrator/register-user";
        }

        modelUser.setEmail(modelUser.getEmail().toLowerCase());
        modelUser.setStatus(1);
        modelUser.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        Role role = new Role();
        role.setId(3);
        modelUser.setRole(role);
        userService.save(modelUser);
        return "redirect:/administrator/manage-user";
    }

    //endregion

    //region REGISTER ADMINISTRATIVE

    /**
     * Get and iniciate data from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return Register user view.
     */
    @GetMapping("/register-administrative")
    public String registerAdministrative(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("client", false);
        model.addAttribute("rutMessage", "");
        model.addAttribute("firstNameMessage", "");
        model.addAttribute("lastNameMessage", "");
        model.addAttribute("emailMessage", "");
        model.addAttribute("phoneMessage", "");
        return "administrator/register-user";
    }

    /**
     * Receives data from register-user.html.
     * @param modelUser User from register-user.html.
     * @param model Is the application's dynamic data structure.
     * @return If all is ok, redirect to administrator/manage-installation.
     */
    @PostMapping("/register-administrative")
    public String registerAdministrative(@ModelAttribute("user") User modelUser, Model model) {

        String[] errorMessages = Validation.registerUserValidation(userService, modelUser);

        if (errorMessages[0].equals("false")){
            model.addAttribute("rutMessage", errorMessages[1]);
            model.addAttribute("client", false);
            model.addAttribute("firstNameMessage", errorMessages[2]);
            model.addAttribute("lastNameMessage", errorMessages[3]);
            model.addAttribute("emailMessage", errorMessages[4]);
            model.addAttribute("phoneMessage", errorMessages[5]);
            return "administrator/register-user";
        }

        modelUser.setEmail(modelUser.getEmail().toLowerCase());
        modelUser.setStatus(1);
        modelUser.setPassword(passwordEncoder.encode(modelUser.getPassword()));
        Role role = new Role();
        role.setId(2);
        modelUser.setRole(role);
        userService.save(modelUser);

        return "redirect:/administrator/manage-user";
    }

    //endregion
}