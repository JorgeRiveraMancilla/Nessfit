package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrative")
public class AdministrativeChangeStatusController {
    @Autowired
    UserServiceInterface userService;

    @Autowired
    InstallationServiceInterface installationService;

    /**
     * Method that return a form.
     * @return the "change-status-user" form.
     */
    @GetMapping("/change-status-user")
    public String changeStatusUser() {
        return "administrative/manage-user";
    }

    /**
     * Changes the user status, depending on the form election by the administrator or administrative.
     * @param rut Rut from user that we change the status.
     * @return return to "change-status-user" page, or home page.
     */
    @PostMapping("/change-status-user/{rut}")
    public String changeStatusUser(@PathVariable String rut) {
        User user = userService.searchByRut(rut);

        user.setStatus(user.getStatus() == 1 ? 0 : 1);
        userService.save(user);
        return "redirect:/administrative/manage-user";
    }
    
    /**
     * Method that return a form.
     * @return the "change-status-installation" form.
     */
    @GetMapping("/change-status-installation")
    public String changeStatusInstallation() {
        return "administrative/manage-installation";
    }

    /**
     * Changes the installation status, depending on the form election by the administrative.
     * @param name Name from installation that we change the status.
     * @return return to "change-status-installation" page, or home page.
     */
    @PostMapping("/change-status-installation/{name}")
    public String changeStatusInstallation(@PathVariable String name) {
        Installation installation = installationService.searchByName(name);

        installation.setStatus(installation.getStatus() == 1 ? 0 : 1);
        installationService.save(installation);
        return "redirect:/administrative/manage-installation";
    }
}