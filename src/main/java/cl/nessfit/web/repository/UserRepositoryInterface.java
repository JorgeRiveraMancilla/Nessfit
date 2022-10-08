package cl.nessfit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nessfit.web.model.User;

import java.util.List;

public interface UserRepositoryInterface extends JpaRepository<User, String> {

    /**
     * Select * from users u where u.rut = rut.
     * @param rut RUT from user.
     * @return User.
     */
    User findByRut(String rut);

    List<User> findByRoleId(int id);
}