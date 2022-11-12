package cl.nessfit.web.controller.client;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.Request;
import cl.nessfit.web.repository.RequestRepositoryInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/client")
public class ClientRentInstallationController {
    @Autowired
    private InstallationServiceInterface installationService;
    @Autowired
    private RequestServiceInterface requestService;

    @GetMapping ("/view-installation")
    public String viewInstallation(Model model) {
        List<Installation> installations = installationService.getInstallations();
        if (installations.isEmpty()) {
            model.addAttribute("installations", null);
        } else {
            model.addAttribute("installations", installationService.getInstallations());
        }
        return "client/view-installation";
    }

    @PostMapping("/view-installation")
    public String viewInstallation(Model model, @RequestParam ("name") String name) {
        model.addAttribute("installations", installationService.searchByName(name));
        return "client/view-installation";
    }

    @GetMapping ("/rent-installation/{name}")
    public String rentInstallation(Model model, @PathVariable String name) {
        Installation installation = installationService.searchByName(name);
        List<Request> requests = requestService.getRequestsBy(name);

        model.addAttribute("installation", installation);
        model.addAttribute("requests", requests);

        return "client/rent-installation";
    }
}