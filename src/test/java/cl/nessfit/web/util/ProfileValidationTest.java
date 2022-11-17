package cl.nessfit.web.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;

class ProfileValidationTest {

	@Mock
	private UserServiceInterface userService;
	
	
	private User editedUser;
	private String email;
	private String rut1;
	private String rut2;
	
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		rut1 = "195379009";
		rut2 = "195379008";
		
		editedUser = new User();
		editedUser.setEmail("hola@hola.ola");
		email = "chao@hola.ola";
	}

	@Test
	void notExistEmail() {
		assertTrue(ProfileValidation.notExistEmail(userService, editedUser, email));
	}
	
	@Test
	void notExistRut() {
		assertTrue(ProfileValidation.notExistRut(userService, rut2));
	}
	
	@Test
	void validRut() {
		assertTrue(ProfileValidation.validRut(rut1));
		assertFalse(ProfileValidation.validRut(rut2));
	}
	
	@Test
	void calculateDV() {
		assertFalse(ProfileValidation.calculateDV(rut1).equals("8"));
	}

}
