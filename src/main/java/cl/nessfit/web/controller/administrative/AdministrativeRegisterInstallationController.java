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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

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

        model.addAttribute("categories", categoryService.getCategories());
        return "administrative/register-installation";
        
        /* 
        // New Installation
        Installation newInstallation = new Installation();

        // Set attributes
        newInstallation.setName(modelInstallation.getName());
        newInstallation.setAddress(modelInstallation.getAddress());
        newInstallation.setRentalCost(modelInstallation.getRentalCost());
        newInstallation.setStatus(modelInstallation.getStatus());
        // Create category
        Category category = new Category();
        // convertir modelInstallation.getCategory() en int
        //category.setId();
        newInstallation.setCategory(category);
        // Save user
        //InstallationService.save(newInstallation);

        return "redirect:/administrative/manage-installation";
        */
    }
}