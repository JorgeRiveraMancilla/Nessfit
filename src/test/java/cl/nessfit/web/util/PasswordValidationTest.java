package cl.nessfit.web.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationTest {

    private String password1;
    private String password2;
    private String password3;
    private String password4;


    @BeforeEach
    void setUp() {
        password1 = "abdcefghij";
        password2 = "abdcefghij";
        password3 = "12345678910";
        password4 = "aaaaaa";
    }

    @Test
    void validatePassword() {
        assertTrue(PasswordValidation.validatePassword(password1, password2));

    }

    @Test
    void areEquals() {
        assertTrue(PasswordValidation.areEquals(password1, password2));
        assertFalse(PasswordValidation.areEquals(password1, password3));
    }

    @Test
    void lengthValidation() {
        assertTrue(PasswordValidation.lengthValidation(password3));
        assertFalse(PasswordValidation.lengthValidation(password4));
    }
}