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
@RequestMapping("/administratative")
public class ChangeStatusAdministrativeController {
    @Autowired
    UserServiceInterface userService;
    /**
     * Method that return a form 
     * @return the "change-status-user" form
     */
    @GetMapping("/change-status-user")
    public String changeStatusUser() {
        return "change-status-user";
    }

    /**
     * Changes the user status, depending on the form election by the administrative
     * @param allRequestParams form data
     * @param model is the application's dynamic data structure
     * @return return to "change-status-user" page, or home page
     */
    @PostMapping("/change-status-user")
    public String changeStatusUser(@RequestParam Map<String,String> allRequestParams, Model model) {
        String rut = allRequestParams.get("rut");
        String status = allRequestParams.get("status");
        User client = userService.searchByRut(rut);
        User userLogged = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        
        if(userLogged.getRole().getId() != 2) {
        	return "change-status-user";
        }
        
        if (status == null) {
        	return "change-status-user";
        }
        if (client == null) {
            model.addAttribute("msgRutExist", false);
        }else {
        	int idUserLogged = userLogged.getRole().getId();
        }
        
        
        
        if (status == null) {
            

            if (client == null) {
                model.addAttribute("msgRutExist", false);
            } else {
                int idUser = client.getRole().getId();
                if (idUser <= idUserLogged) {
                    if (userLogged.getRole().getId() == 1) {
                        model.addAttribute("msgChangeAdministrator", false);
                    } else {
                        model.addAttribute("msgChangeAdministrative", false);
                    }
                } else {
                    model.addAttribute("msgChange", "");
                    model.addAttribute("msgRut", rut);
                    model.addAttribute("msgName", client.getFirstName());
                    model.addAttribute("msgStatus", client.getStatus() == 0 ? "Deshabilitado" : "Habilitado");
                }
            }
            return "change-status-user";
        } else {
            client.setStatus(status.equals("Habilitado") ? 0 : 1);
            userService.save(client);
            return "redirect:/";
        }
    }
}