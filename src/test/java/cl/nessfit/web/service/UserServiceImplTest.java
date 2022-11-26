package cl.nessfit.web.service;

import cl.nessfit.web.model.User;
import cl.nessfit.web.repository.UserRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepositoryInterface userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
    }

    @Test
    void save() {
        when(userRepository.save(user)).thenReturn(user);
        userService.save(user);
    }

    @Test
    void searchByRut() {
        when(userRepository.findByRut(any())).thenReturn(user); // User
        assertNotNull(userService.searchByRut(any()));
    }

    @Test
    void getAdministrative() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertNotNull(userService.getAdministratives());
    }

}