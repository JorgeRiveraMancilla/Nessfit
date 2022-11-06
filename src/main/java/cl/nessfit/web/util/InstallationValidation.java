package cl.nessfit.web.util;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InstallationValidation {
    public static boolean existName(InstallationServiceInterface installationService, String name) {
        // We check if the name currently exists in the system.
        List<Installation> installationList = installationService.getInstallations();
        for (Installation installation : installationList) {
            // If the name exists in the system, then we return false.
            if (installation.getName().equalsIgnoreCase(name)) { return true; }
        }
        return false;
    }

    public static boolean validMinimum(String rentalCost) {
        return 1000 <= Integer.parseInt(rentalCost);
    }

    public static boolean validMaximum(String rentalCost) {
        try {
            int maximum = Integer.parseInt(rentalCost);
            return maximum <= 100000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validCategory(CategoryServiceInterface categoryService, String category) {
        return categoryService.exists(category);
    }
}