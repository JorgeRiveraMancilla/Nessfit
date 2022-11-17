package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Category;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.util.CategoryValidation;
import cl.nessfit.web.util.Validation;
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
    private CategoryServiceInterface categoryService;
    @Autowired
    private InstallationServiceInterface installationService;

    @GetMapping("/register-installation")
    public String registerInstallations(Model model) {
        Installation installation = new Installation();
        model.addAttribute("installation", installation);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("nameMessage", "");
        model.addAttribute("addressMessage", "");
        model.addAttribute("rentalCostMessage", "");
        return "administrative/register-installation";
    }

    @PostMapping("/register-installation")
    public String registerInstallations(@Valid @ModelAttribute("installation") Installation modelInstallation,
                                        BindingResult bindingResult,
                                        @RequestParam Map<String, String> allParams,
                                        Model model) {
        String[] errors = Validation.registerInstallationValidation(installationService, modelInstallation);

        if (errors[0].equals("false")){
            model.addAttribute("nameMessage", errors[1]);
            model.addAttribute("addressMessage", errors[2]);
            model.addAttribute("rentalCostMessage", errors[3]);
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
            return "administrative/register-installation";
        }

        Installation installation = new Installation();
        installation.setName(modelInstallation.getName().strip());
        installation.setAddress(modelInstallation.getAddress().strip());
        installation.setRentalCost(modelInstallation.getRentalCost());
        installation.setStatus(Integer.parseInt(allParams.get("status")));

        Category category = new Category();
        category.setId(categoryService.searchByName(nameCategory).getId());
        category.setName(nameCategory);

        installation.setCategory(category);

        installationService.save(installation);

        return "redirect:/administrative/manage-installation";
    }
}