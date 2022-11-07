package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Category;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.util.InstallationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrative")
public class AdministrativeRegisterInstallationController {
    @Autowired
    CategoryServiceInterface categoryService;

    @Autowired
    InstallationServiceInterface installationService;

    @GetMapping("/register-installation")
    public String registerInstallations(Model model) {
        Installation installation = new Installation();
        model.addAttribute("installation", installation);
        model.addAttribute("categories", categoryService.getCategories());
        return "administrative/register-installation";
    }

    @PostMapping("/register-installation")
    public String registerInstallations(@Valid @ModelAttribute("installation") Installation modelInstallation,
                                        BindingResult bindingResult,
                                        @RequestParam Map<String, String> allParams,
                                        Model model) {
        String type = allParams.get("type");

        if (!InstallationValidation.validCategory(categoryService, type)) {
            model.addAttribute("validType", false);
        } else {
            model.addAttribute("category", type);
        }

//        if (operative != null) {
//            model.addAttribute("operative", true);
//        } else if (inoperative != null) {
//            model.addAttribute("inoperative", true);
//        } else {
//            model.addAttribute("validSelect", false);
//        }



        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "administrative/register-installation";
        }

        if (InstallationValidation.existName(installationService, modelInstallation.getName())) {
            model.addAttribute("existName", true);
        }

        if (!InstallationValidation.validMaximum(modelInstallation.getRentalCost())) {
            model.addAttribute("validMaximum", false);
        }
        else if (!InstallationValidation.validMinimum(modelInstallation.getRentalCost())) {
            model.addAttribute("validMinimum", false);
        }

        Installation newInstallation = new Installation();

        newInstallation.setName(modelInstallation.getName());
        newInstallation.setAddress(modelInstallation.getAddress());
        newInstallation.setRentalCost(modelInstallation.getRentalCost());
        newInstallation.setStatus(1);

        Category category = new Category();
        category.setId(1);
        category.setName("Cancha");
        newInstallation.setCategory(category);
        installationService.save(newInstallation);

        model.addAttribute("categories", categoryService.getCategories());
        return "administrative/manage-installation";
    }
}