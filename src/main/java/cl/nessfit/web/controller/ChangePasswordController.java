package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ChangePasswordController {

    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/change-password")
    public String password(Model model){
        //Password1 = new password.
        model.addAttribute("password1", "");
        //Password2 = repeat new password.
        model.addAttribute("password2", "");
        return "password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword, @RequestParam("repeatNewPassword")
        String repeatNewPassword, HttpServletRequest request, RedirectAttributes attributes, Model model){

        // Obtain the user
        User user = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());

        //validar contraseña

        if (!PasswordValidation.validatePassword(newPassword, repeatNewPassword)) {
            model.addAttribute("msg", "Contraseña incorrecta");
            model.addAttribute("newPassword", newPassword);
            model.addAttribute("repeatNewPassword", repeatNewPassword);
            return "password";
	    }

        // Set new password (encrypted)
        user.setPassword(passwordEncoder.encode(newPassword));

        userService.save(user);

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";

    }

    @ModelAttribute("rutUser")
    public String auth() { return SecurityContextHolder.getContext().getAuthentication().getName(); }

}
