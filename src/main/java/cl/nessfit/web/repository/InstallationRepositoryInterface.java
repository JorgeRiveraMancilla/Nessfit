package cl.nessfit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nessfit.web.model.Installation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstallationRepositoryInterface extends JpaRepository<Installation, String> {

    Installation findById(int id);

    /**
     * Select * from installations e where e.name = name.
     * @param name name from installation.
     * @return Installation.
     */
    Installation findByName(String name);

    /**
     * Select *;
     * @return All installations from the database.
     */
    List<Installation> findAll();

    /**
     * Select * from installation e where e.status = status.
     * @param status Status from installation.
     * @return All installations with the same status.
     */
    List<Installation> findAllByStatus(int status);
}