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

    List<User> findByRoleId(int id);

    /**
     * Select *;
     * @return All users from the database.
     */
    List<User> findAll();

    /*
    @Query (value = "select * from users where rut = ?")
    List<User> metodo(String rut);*/

    /* SELECT * FROM users u WHERE u.rut = ? AND u.id < ? */
    User findByRutAndRoleIdLessThan(String rut, int roleId);
}