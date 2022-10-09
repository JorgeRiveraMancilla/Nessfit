package cl.nessfit.web.util;

public class PasswordValidation {
    /**
     * Method that performs validations corresponding to the password.
     * @param password1 New password.
     * @param password2 Repeat new password.
     * @return "True" if the password validation is ok, and "False" if not.
     */
    public static boolean validatePassword(String password1, String password2) {
        if (areEquals(password1, password2)) {
            return lengthValidation(password1);
        }
        return false;
    }

    public static boolean areEquals(String password1, String password2) {
        return password1.equals(password2);
    }

    public static boolean lengthValidation(String password) {
        if (password.length() >= 10) {
            return password.length() <= 15;
        }
        return false;
    }
}