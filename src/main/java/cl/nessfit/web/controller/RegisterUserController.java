package cl.nessfit.web.controller;

import cl.nessfit.web.model.Role;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
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
public class RegisterUserController {
    @Autowired
    UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register-client")
    public String registerClient(Model model) {
        model.addAttribute("client", true);
        return "administrator/register-user";
    }

    @GetMapping("/register-administrative")
    public String registerAdministrative(Model model) {
        model.addAttribute("client", false);
        return "administrator/register-user";
    }

    @PostMapping ("/register-administrative")
    public String registerAdministrative(@RequestParam("rut") String rut, @RequestParam("name") String firstName, @RequestParam("lastname") String lastName,
                                         @RequestParam("email") String email, @RequestParam("phone") String phone, Model model, HttpServletRequest request) {

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
            // crear atributos y mostrar en html
            model.addAttribute("rut", rut);
            model.addAttribute("name", firstName);
            model.addAttribute("lastname", lastName);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);

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