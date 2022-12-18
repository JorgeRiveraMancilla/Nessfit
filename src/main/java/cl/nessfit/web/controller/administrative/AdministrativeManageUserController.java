package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
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
@RequestMapping("/administrative")
public class AdministrativeManageUserController {
    @Autowired
    private UserServiceInterface userService;

    /**
     * Get data from manage-user.html.
     * @param model Is the application's dynamic data structure.
     * @return Manage user view.
     */
    @GetMapping("/manage-user")
    public String manageUsers(Model model) {
        List<User> users = userService.getClients();
        if (!users.isEmpty()) {
            model.addAttribute("users", users);
        }
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrative/manage-user";
    }

    /**
     * Receives data from manage-user.html.
     * @param rut User's rut.
     * @param model Is the application's dynamic data structure.
     * @return Manage user page.
     */
    @PostMapping("/manage-user")
    public String manageUser(@RequestParam("rut") String rut, Model model) {
        User user  = userService.searchByRut(rut);
        if (user != null) {
            if (user.getRole().getId() == 2 || user.getRole().getId() == 3) {
                model.addAttribute("users", user);
            } else {
                model.addAttribute("users", null);
            }
        } else {
            model.addAttribute("users", null);
        }
        model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "administrative/manage-user";
    }
}