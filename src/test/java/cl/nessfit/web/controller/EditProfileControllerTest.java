package cl.nessfit.web.controller;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.web.servlet.function.ServerResponse.status;

@WebMvcTest(EditProfileController.class)
class EditProfileControllerTest {

    //Permite llamar o hacer peticiones a un controlador.
    @Autowired
    private MockMvc mockMvc;
    //Sirve para crear un simulacro del servicio.
    @MockBean
    private UserServiceInterface userService;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
    }

    @Test
    void editProfile() {

    }

    @Test
    void testEditProfile() throws Exception {
        when(userService.searchByRut(any())).thenReturn(user);
        mockMvc.perform(get("/edit-profile/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}