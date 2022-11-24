package cl.nessfit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.nessfit.web.model.User;
import cl.nessfit.web.repository.UserRepositoryInterface;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    private UserRepositoryInterface userRepository;

    /**
     * Saves the user in the database.
     * @param user User to save.
     */
    @Override
    public void save(User user) { userRepository.save(user); }

    /**
     * Given a rut, returns a user with that rut data.
     * @param rut Rut to search for a user.
     * @return User with that rut data.
     */
    @Override
    public User searchByRut(String rut) { return userRepository.findByRut(rut); }

    /**
     * Given an email, returns a user with that email data.
     * @param email Email to search for a user.
     * @return User with that email data.
     */
    @Override
    public User searchByEmail(String email) { return userRepository.findByEmail(email); }

    /**
     * Return a list with all users with administrative role from the database.
     * @return List with all users with administrative role.
     */
    @Override
    public List<User> getAdministratives() { return userRepository.findByRoleId(2); }

    /**
     * Return a list with all users with client role from the database.
     * @return List with all users with client role.
     */
    @Override
    public List<User> getClients() { return userRepository.findByRoleId(3); }

}