package cl.nessfit.web.service;

import cl.nessfit.web.model.User;

import java.util.List;

public interface UserServiceInterface {

    /**
     * Saves the user in the database.
     * @param user User to save.
     */
    void save(User user);

    /**
     * Given a rut, returns a user with that rut data.
     * @param rut Rut to search for a user.
     * @return User with that rut data.
     */
    User searchByRut(String rut);

    /**
     * Given an email, returns a user with that email data.
     * @param email Email to search for a user.
     * @return User with that email data.
     */
    User searchByEmail(String email);

    /**
     * Return a list with all users with administrative role from the database.
     * @return List with all users with administrative role.
     */
    List<User> getAdministratives();

    /**
     * Return a list with all users with client role from the database.
     * @return List with all users with client role.
     */
    List<User> getClients();

}