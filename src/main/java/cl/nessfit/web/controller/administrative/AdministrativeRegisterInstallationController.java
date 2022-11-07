package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Category;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.util.CategoryValidation;
import cl.nessfit.web.util.InstallationValidation;
import cl.nessfit.web.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
        // Name
        String name = modelInstallation.getName().strip();
        if (Util.isBlank(name)) {
            model.addAttribute("nameIsBlank", true);
        } else if (!Util.validSize(name, 0, 200)) {
            model.addAttribute("validNameSize", false);
        } else if (InstallationValidation.exists(installationService, name)) {
            model.addAttribute("nameExists", true);
        }

        // Address
        String address = modelInstallation.getAddress();
        if (Util.isBlank(address)) {
            model.addAttribute("addressIsBlank", true);
        } else if (!Util.validSize(address, 0, 200)) {
            model.addAttribute("validAddressSize", false);
        }

        // Rental cost
        String rentalCostStr = modelInstallation.getRentalCost();
        int rentalCost;
        if (Util.isBlank(rentalCostStr)) {
            model.addAttribute("rentalCostIsBlank", true);
        } else if (!Util.tryParseInt(rentalCostStr)) {
            model.addAttribute("validRentalCostFormat", false);
        } else {
            rentalCost = Integer.parseInt(rentalCostStr);
            if (!Util.validMin(rentalCost, 1000)) {
                model.addAttribute("validMinimumRentalCost", false);
            } else if (!Util.validMax(rentalCost, 100000)) {
                model.addAttribute("validMaximumRentalCost", false);
            }
        }

        // Category
        String nameCategory = allParams.get("category");
        if (!CategoryValidation.exists(categoryService, nameCategory)) {
            model.addAttribute("selectedOption", false);
        }

        // Status
        int status = Integer.parseInt(allParams.get("status"));

        if (2 < model.asMap().size()) {
            model.addAttribute("categories", categoryService.getCategories());
            if (!model.containsAttribute("selectedOption")) {
                model.addAttribute("nameCategory", nameCategory);
            }
            return "administrative/register-installation";
        }

        Installation installation = new Installation();
        installation.setName(name);
        installation.setAddress(address);
        installation.setRentalCost(modelInstallation.getRentalCost());
        installation.setStatus(status);

        Category category = new Category();
        category.setId(categoryService.searchByName(nameCategory).getId());
        category.setName(nameCategory);

        installation.setCategory(category);

        installationService.save(installation);

        return "redirect:/administrative/manage-installation";
    }
}