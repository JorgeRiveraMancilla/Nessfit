package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.InstallationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/administrative")
public class AdministrativeManageInstallationController {
    @Autowired
    private InstallationServiceInterface installationService;

    /**
     * Get data from manage-installation.html.
     * @param model Is the application's dynamic data structure.
     * @return Manage installation view.
     */
    @GetMapping ("/manage-installation")
    public String manageInstallations(Model model) {
        List<Installation> installations = installationService.getInstallations();
        if (installations.isEmpty()) {
            model.addAttribute("installations", null);
        } else {
            model.addAttribute("installations", installationService.getInstallations());
        }
        return "administrative/manage-installation";
    }

    /**
     * Receives data from manage-installation.html.
     * @param model Is the application's dynamic data structure.
     * @param name Installation name.
     * @return Manage installation page.
     */
    @PostMapping("/manage-installation")
    public String manageInstallations(Model model, @RequestParam("name") String name) {
        model.addAttribute("installations", installationService.searchByName(name));
        return "administrative/manage-installation";
    }
}