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

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User searchByRut(String rut) {
        return userRepository.findByRut(rut);
    }

    @Override
    public List<User> getAdministrativos() {
        return userRepository.findByRoleId(2);
    }

    @Override
    public List<User> getUsers() { return userRepository.findAll(); }

    @Override
    public User searchByRutAndRoleIdLessThan(String rut, int roleId) {
        return userRepository.findByRutAndRoleIdLessThan(rut, roleId);
    }
}