package cl.nessfit.web.service;

import cl.nessfit.web.model.Installation;
import java.util.List;

public interface InstallationServiceInterface {

    Installation searchById(int id);

    /**
     * Saves the installation in the database.
     * @param installation User to save.
     */
    void save(Installation installation);

    /**
     * Given a rut, returns an installation with that name data.
     * @param name Name to search for an installation.
     * @return Installation with that name data.
     */
    Installation searchByName(String name);

    /**
     * Return a list with all installations from the database.
     * @return List with all installations.
     */
    List<Installation> getInstallations();

    /**
     * Returns a list with all installations that match the status in the database.
     * @param status Installation status.
     * @return List with all installations that match the status.
     */
    List<Installation> getInstallationsBy(int status);
}
