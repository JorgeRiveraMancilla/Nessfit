package cl.nessfit.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.nessfit.web.model.User;
import cl.nessfit.web.repository.UserRepositoryInterface;

@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    private UserRepositoryInterface userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User searchBy(String rut) {
        User user = userRepository.findByRut(rut);
        return user;
    }
}