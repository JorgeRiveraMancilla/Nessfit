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
@RequestMapping ("/administrative")
public class AdministrativeEditInstallationController {
    @Autowired
    private CategoryServiceInterface categoryService;

    @Autowired
    private InstallationServiceInterface installationService;
    
    @GetMapping ("/edit-installation/{name}")
    public String editInstallation(Model model, @PathVariable String name) {
        Installation installation = installationService.searchByName(name);
        model.addAttribute("installation", installation);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("nameCategory", installation.getCategory().getName());
        return "administrative/edit-installation";
    }

    /**
     * Receive data from edit-installation.html.
     * @param modelInstallation Installation from the html form.
     * @param bindingResult Result from Installation class validations.
     * @param model Is the application's dynamic data structure.
     * @return Return Installation to home page.
     */
    @PostMapping("/edit-installation")
    public String editInstallation(@Valid @ModelAttribute("installation") Installation modelInstallation,
                              BindingResult bindingResult,
                              @RequestParam Map<String, String> allParams,
                              Model model) {
        // Installation obtained by name
        Installation editedInstallation = installationService.searchByName(modelInstallation.getName().strip());
        
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

        if (2 < model.asMap().size()) {
            model.addAttribute("categories", categoryService.getCategories());
            if (!model.containsAttribute("selectedOption")) {
                model.addAttribute("nameCategory", nameCategory);
            }
            return "administrative/edit-installation";
        }
        
        // Update user values
        editedInstallation.setName(modelInstallation.getName());
        editedInstallation.setAddress(modelInstallation.getAddress());
        editedInstallation.setRentalCost(modelInstallation.getRentalCost());
        editedInstallation.setStatus(installationService.searchByName(modelInstallation.getName()).getStatus());
        Category category = new Category();
        category.setId(categoryService.searchByName(nameCategory).getId());
        category.setName(nameCategory);
        editedInstallation.setCategory(category);
        // Save data from actualInstallation
        installationService.save(editedInstallation);

        return "redirect:/administrative/manage-installation";
    }
}

