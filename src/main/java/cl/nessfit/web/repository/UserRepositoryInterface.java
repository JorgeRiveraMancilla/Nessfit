package cl.nessfit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nessfit.web.model.User;

public interface UserRepositoryInterface extends JpaRepository<User, String> {
    User findByRut(String rut);
}