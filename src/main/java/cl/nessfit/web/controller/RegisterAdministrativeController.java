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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/administrator")
public class RegisterAdministrativeController {

    @Autowired
    UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Mapping for register user.
     * @param model is the application's dynamic data structure.
     * @return Redirect to register-user.html form.
     */
    @GetMapping("/register-user")
    public String registerUser(Model model) {
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "register-user";
    }

    /**
     * Registers a new user with the form data
     * @param rut Rut data of the new user
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email
     * @param phone User's phone number
     * @param model is the application's dynamic data structure
     * @return if profile is not valid, return "register-user", else save new user and redirect to home page
     */
    @PostMapping("/register-user")
    public String registerNewUser(@RequestParam("rut") String rut, @RequestParam("name") String firstName, @RequestParam("lastname") String lastName,
                              @RequestParam("email") String email, @RequestParam("phone") String phone, Model model, HttpServletRequest request) {

        User loggedUser = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());

        // if loggedUser is null or not exist, logout.
        if (loggedUser == null){
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);
            return "redirect:/";
        }

        // if loggedUser is not ADMINISTRATOR, then redirect to home.
        if (loggedUser.getRole().getId() != 1) { return "redirect:/"; }

        // status[] = {systemStatus, name, lastName, phone, emailExist, emailValidator}
        boolean[] status = ProfileValidation.isValidRegister(userService, rut, firstName, lastName, phone, email);

        // Validate profile.
        if (!status[0]) {
            // Error messages
            model.addAttribute("msgRutExists", status[1]);
            model.addAttribute("msgRutValidator", status[2]);
            model.addAttribute("msgName", status[3]);
            model.addAttribute("msgLastName", status[4]);
            model.addAttribute("msgEmailExist", status[5]);
            model.addAttribute("msgEmailValidator", status[6]);
            model.addAttribute("msgPhone", status[7]);

            return "register-user";
        }

        User newUser = new User();

        // Set attributes.
        newUser.setRut(rut);
        newUser.setFirstName(ProfileValidation.newNamesEdit(firstName));
        newUser.setLastName(ProfileValidation.newNamesEdit(lastName));
        newUser.setPhone(Long.parseLong(phone));
        newUser.setEmail(email);
        newUser.setStatus(1);
        newUser.setPassword(passwordEncoder.encode(rut));

        Role role = new Role();
        role.setId(2);

        newUser.setRole(role);

        // Save user.
        userService.save(newUser);

        return "redirect:/";
    }
}