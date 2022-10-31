package cl.nessfit.web.controller;

import cl.nessfit.web.model.Role;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/administrator")
public class RegisterAdministrativeController {

    @Autowired
    UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Mapping for register user.
     * @param model Is the application's dynamic data structure.
     * @return Redirect to register-user.html form.
     */
    @GetMapping("/register-user")
    public String registerUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register-user";
    }

    /**
     * Registers a new user with the form data.
     * @param modelUser User from the html form.
     * @param bindingResult Result from User class validations.
     * @param model Is the application's dynamic data structure.
     * @return if profile is not valid, return "register-user", else save new user and redirect to home page.
     */
    @PostMapping("/register-user")
    public String registerNewUser(@Valid @ModelAttribute("user") User modelUser, BindingResult bindingResult, Model model) {

        // if loggedUser is not ADMINISTRATOR, then redirect to home
        User loggedUser = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedUser.getRole().getId() != 1) { return "redirect:/"; }

        // Extra verifications
        boolean existEmail = ProfileValidation.notExistEmail(userService,modelUser.getEmail());
        boolean existRut = ProfileValidation.notExistRut(userService,modelUser.getRut());
        boolean validRut = ProfileValidation.validRut(modelUser.getRut());

        // If there is a problem, it is verified
        if (bindingResult.hasErrors() || !existEmail || !existRut || !validRut) {
            model.addAttribute("existEmail", existEmail);
            model.addAttribute("existRut", existRut);
            model.addAttribute("validRut", validRut);
            model.addAttribute("rut", modelUser.getRut());
            return "register-user";
        }
        // New User
        User newUser = new User();
        // Set attributes
        newUser.setRut(modelUser.getRut());
        newUser.setFirstName(modelUser.getFirstName());
        newUser.setLastName(modelUser.getLastName());
        newUser.setPhone(modelUser.getPhone());
        newUser.setEmail(modelUser.getEmail());
        newUser.setStatus(1);
        newUser.setPassword(passwordEncoder.encode(modelUser.getRut()));
        // Create role
        Role role = new Role();
        role.setId(2);
        newUser.setRole(role);
        // Save user
        userService.save(newUser);

        return "redirect:/";
    }
}