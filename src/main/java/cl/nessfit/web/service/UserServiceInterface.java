package cl.nessfit.web.service;

import cl.nessfit.web.model.User;

import java.util.List;

public interface UserServiceInterface {

    void save(User user);

    User searchByRut(String rut);

    List<User> getAdministrativos();

    List<User> getUsers();

    User searchByRutAndRoleIdLessThan(String rut, int roleId);
}