package cl.nessfit.web.controller;

import javax.validation.Valid;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
import cl.nessfit.web.util.InstallationValidation;

@Controller
@RequestMapping("/administrative")
public class RegisterAdministrativeInstallationController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    InstallationServiceInterface installationService;

    public RegisterAdministrativeInstallationController(InstallationServiceInterface installationService) {
        this.installationService = installationService;
    }

    /**
     * Mapping for register installation.
     * @param model Is the application's dynamic data structure.
     * @return Redirect to register-installation.html form.
     */
    @GetMapping("/register-installation")
    public String registerInstallation(Model model) {
        Installation installation = new Installation();
        model.addAttribute("installation", installation);
        return "register-installation";
    }

    /**
     * Registers a new installation with the form data.
     * @param modelInstallation Installation from the html form.
     * @param bindingResult Result from Installation class validations.
     * @param model Is the application's dynamic data structure.
     * @return if profile is not valid, return "register-installation", else save new user and redirect to home page.
     */
    @PostMapping("/register-installation")
    public String registerNewInstallation(@Valid @ModelAttribute("installation") Installation modelInstallation, BindingResult bindingResult, Model model) {

        // if loggedUser is not ADMINISTRATIVE, then redirect to home
        User loggedUser = userService.searchByRut(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedUser.getRole().getId() != 2) { return "redirect:/"; }

        // Extra verifications
        boolean existName = InstallationValidation.notExistName(installationService,modelInstallation.getName());

        //If there is a problem, it is verified
        if (bindingResult.hasErrors() || !existName) {
            model.addAttribute("existName", existName);
            return "register-installation";
        }
        
        // New installation
        Installation newInstallation = new Installation();
        // Set attributes
        newInstallation.setName(modelInstallation.getName());
        newInstallation.setAddress(modelInstallation.getAddress());
        newInstallation.setType(modelInstallation.getType());
        newInstallation.setRentalCost(modelInstallation.getRentalCost());
        newInstallation.setStatus(1);
        // Save installation
        installationService.save(newInstallation);

        return "redirect:/";
    }
}
