package cl.nessfit.web.repository;

import cl.nessfit.web.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepositoryInterface extends JpaRepository<Request, Integer> {
    List<Request> findRequestsByInstallation_Name(String name);
}