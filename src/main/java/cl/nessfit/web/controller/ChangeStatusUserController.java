package cl.nessfit.web.controller;

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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/administrator")
public class ChangeStatusUserController {
    @Autowired
    UserServiceInterface userService;

    @GetMapping("/change-status-user")
    public String changeStatusUser() {
        return "change-status-user";
    }
    /**
     * Changes the user status, depending on the form election by the administrator or administrative
     * @param allRequestParams form data
     * @param model project model
     * @return return to "change-status-user" page, or home page
     */
    @PostMapping("/change-status-user")
    public String changeStatusUser(@RequestParam Map<String,String> allRequestParams, Model model) {
        String rut = allRequestParams.get("rut");
        String status = allRequestParams.get("status");
        User user = userService.searchByRut(rut);

        if (status == null) {
            User userLogged = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
            int idUserLogged = userLogged.getRole().getId();

            if (user == null) {
                model.addAttribute("msgRutExist", false);
            } else {
                int idUser = user.getRole().getId();
                if (idUser <= idUserLogged) {
                    if (userLogged.getRole().getId() == 1) {
                        model.addAttribute("msgChangeAdministrator", false);
                    } else {
                        model.addAttribute("msgChangeAdministrative", false);
                    }
                } else {
                    model.addAttribute("msgChange", "");
                    model.addAttribute("msgRut", rut);
                    model.addAttribute("msgName", user.getFirstName());
                    model.addAttribute("msgStatus", user.getStatus() == 0 ? "Deshabilitado" : "Habilitado");
                }
            }
            return "change-status-user";
        } else {
            user.setStatus(status.equals("Habilitado") ? 0 : 1);
            userService.save(user);
            return "redirect:/";
        }
    }
}