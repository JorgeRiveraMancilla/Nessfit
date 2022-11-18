package cl.nessfit.web.controller.administrative;

import cl.nessfit.web.model.Category;
import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;

import cl.nessfit.web.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping ("/administrative")
public class AdministrativeEditInstallationController {
    @Autowired
    private CategoryServiceInterface categoryService;

    @Autowired
    private InstallationServiceInterface installationService;

    /**
     * Get data from edit-installation.html.
     * @param model Is the application's dynamic data structure.
     * @param name Installation name.
     * @return Return the edit installation page.
     */
    @GetMapping ("/edit-installation/{id}")
    public String editInstallation(Model model, @PathVariable int id) {
        Installation installation = installationService.searchById(id);
        model.addAttribute("installation", installation);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("nameCategory", installation.getCategory().getName());
        model.addAttribute("addressMessage", "");
        model.addAttribute("rentalCostMessage", "");
        return "administrative/edit-installation";
    }

    /**
     * Receives data from edit-installation.html.
     * @param modelInstallation Installation from the html form.
     * @param model Is the application's dynamic data structure.
     * @return Return Installation to home page.
     */
    @PostMapping("/edit-installation")
    public String editInstallation(@ModelAttribute("installation") Installation modelInstallation,
                                   BindingResult bindingResult,
                                   @RequestParam Map<String, String> allParams,
                                   Model model) {
        String[] errors = Validation.editInstallationValidation(modelInstallation);

        if (errors[0].equals("false")){
            model.addAttribute("addressMessage", errors[1]);
            model.addAttribute("rentalCostMessage", errors[2]);
        }

        // Category
        String nameCategory = allParams.get("category");
        if (!Validation.existsCategory(categoryService, nameCategory)) {
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
        Category category = new Category();
        category.setId(categoryService.searchByName(nameCategory).getId());
        category.setName(nameCategory);
        modelInstallation.setCategory(category);
        modelInstallation.setStatus(installationService.searchByName(modelInstallation.getName()).getStatus());
        // Save data from actualInstallation
        installationService.save(modelInstallation);

        return "redirect:/administrative/manage-installation";
    }
}

