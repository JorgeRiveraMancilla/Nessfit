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
@RequestMapping("/administrative")
public class RegisterAdministrativeInstallationController {

    /**
     * Mapping for register installation.
     * @param model is the application's dynamic data structure.
     * @return Redirect to register-installation.html form.
     */
    @GetMapping("/register-installation")
    public String registerInstallation(Model model) {
        
        return "register-installation";
    }

}