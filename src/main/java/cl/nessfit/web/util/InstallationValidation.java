package cl.nessfit.web.util;

import cl.nessfit.web.service.InstallationServiceInterface;

public class InstallationValidation {
    public static boolean exists(InstallationServiceInterface installationService, String name) {
        return installationService.searchByName(name) != null;
    }
}