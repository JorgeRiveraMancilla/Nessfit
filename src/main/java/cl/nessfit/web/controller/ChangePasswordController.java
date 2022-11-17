package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ChangePasswordController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Method that receives a model type variable and implements the new password structure in it.
     * @return returns "change-password" string.
     */
    @GetMapping("/change-password")
    public String password(Model model) {
        model.addAttribute("rut", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("passwordMsg", "");
        return "change-password";
    }

    /**
     * Method that handles the changing of a password and logs the corresponding data into the user class.
     * @param newPassword New password entered by the user.
     * @param repeatNewPassword Validation of the new password.
     * @param request http request data.
     * @param model The model of the project.
     * @return "change-password" if the process was unsuccessful otherwise logouts and returns "redirect:/"
     */
    @PostMapping("/change-password")
    public String password(@RequestParam("newPassword") String newPassword,
                           @RequestParam("repeatNewPassword") String repeatNewPassword,
                           HttpServletRequest request,
                           Model model) {

        // Obtain the user
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());

        String validator = Validation.validPassword(newPassword, repeatNewPassword);

        if (!validator.equals("")){
            model.addAttribute("passwordMsg", validator);
            model.addAttribute("newPassword", newPassword);
            model.addAttribute("repeatNewPassword", repeatNewPassword);
            model.addAttribute("rut", user.getRut());
            return "change-password";
        }

        // Set new password (encrypted)
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }
}