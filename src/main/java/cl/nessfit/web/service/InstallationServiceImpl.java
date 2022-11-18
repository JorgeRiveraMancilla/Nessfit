package cl.nessfit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.repository.InstallationRepositoryInterface;

import java.util.List;

@Service
public class InstallationServiceImpl implements InstallationServiceInterface{
    @Autowired
    private InstallationRepositoryInterface installationRepository;

    @Override
    public Installation searchById(int id) {
        return installationRepository.findById(id);
    }

    /**
     * Saves the installation in the database.
     * @param installation User to save.
     */
    @Override
    public void save(Installation installation) {
        installationRepository.save(installation);
    }

    /**
     * Given a rut, returns an installation with that name data.
     * @param name Name to search for an installation.
     * @return Installation with that name data.
     */
    @Override
    public Installation searchByName(String name) {
        return installationRepository.findByName(name);
    }

    /**
     * Return a list with all installations from the database.
     * @return List with all installations.
     */
    @Override
    public List<Installation> getInstallations() { return installationRepository.findAll(); }

    /**
     * Returns a list with all installations that match the status in the database.
     * @param status Installation status.
     * @return List with all installations that match the status.
     */
    @Override
    public List<Installation> getInstallationsBy(int status) {
        return installationRepository.findAllByStatus(status);
    }

}
