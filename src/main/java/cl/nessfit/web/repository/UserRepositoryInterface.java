package cl.nessfit.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nessfit.web.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepositoryInterface extends JpaRepository<User, String> {

    /**
     * Select * from users u where u.rut = rut.
     * @param rut RUT from user.
     * @return User.
     */
    User findByRut(String rut);

    /**
     * Select * from users u where u.email = email.
     * @param email Email from user.
     * @return User.
     */
    User findByEmail(String email);

    /**
     * Select * form users u where u.id_role = id.
     * @param id ID Role.
     * @return All user with the same id.
     */
    List<User> findByRoleId(int id);

    /**
     * Select *;
     * @return All users from the database.
     */
    List<User> findAll();

}