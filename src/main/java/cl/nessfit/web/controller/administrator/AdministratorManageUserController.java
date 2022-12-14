package cl.nessfit.web.controller.administrator;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping ("/administrator")
public class AdministratorManageUserController {
    @Autowired
    private UserServiceInterface userService;

    /**
     * Get data from manage-user.html.
     * @param model Is the application's dynamic data structure.
     * @return Manage user view.
     */
    @GetMapping ("/manage-user")
    public String manageUser(Model model) {
        List<User> users =  Validation.joinList(userService.getAdministratives(), userService.getClients());
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrator/manage-user";
    }

    /**
     * Receives data from manage-user.html.
     * @param rut User's rut.
     * @param model Is the application's dynamic data structure.
     * @return Manage user page.
     */
    @PostMapping("/manage-user")
    public String manageUser(@RequestParam("rut") String rut, Model model) {
        model.addAttribute("users", userService.searchByRut(rut));
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrator/manage-user";
    }
}