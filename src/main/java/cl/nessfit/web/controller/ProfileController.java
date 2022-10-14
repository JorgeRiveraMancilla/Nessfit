package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    UserServiceInterface userService;

    /**
     * Check if user is authenticated
     * @param model
     * @return access to the webpage
     */
    @GetMapping ("/edit-profile")
    public String editProfile(Model model) {
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "edit-profile";
    }

    /**
     * receive data from edit profile  html. 
     * @param firstName user's FirstName data
     * @param lastName user's lastName data
     * @param email user's email data 
     * @param phone user's phone number data
     * @param model is the application's dynamic data structure
     * @return return user to home page
     */
    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam("name") String firstName, @RequestParam("lastname") String lastName,
                              @RequestParam("email") String email, @RequestParam("phone") String phone, Model model, HttpServletRequest request){
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());

        // validate if the user exist.
        if (user == null){
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, null, null);
            return "redirect:/";
        }

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
            model.addAttribute("name", firstName);
            model.addAttribute("lastname", lastName);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            return "edit-profile";
        }
        // Set new attributes.
        user.setFirstName(ProfileValidation.newNamesEdit(firstName));
        user.setLastName(ProfileValidation.newNamesEdit(lastName));
        user.setEmail(email);
        user.setPhone(Long.parseLong(phone));
        // Save user.
        userService.save(user);
        return "redirect:/";
    }
}