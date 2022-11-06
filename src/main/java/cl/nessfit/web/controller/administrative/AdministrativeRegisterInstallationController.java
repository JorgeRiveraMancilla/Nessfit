package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.InstallationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrative")
public class AdministrativeRegisterInstallationController {
    @Autowired
    InstallationServiceInterface installationService;

    @GetMapping("/register-installation")
    public String registerInstallations() {
        return "administrative/register-installation";
    }
}