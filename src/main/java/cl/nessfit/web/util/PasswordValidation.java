package cl.nessfit.web.util;

public class PasswordValidation {

    public static boolean validatePassword(String password1, String password2){
        if (password1.equals(password2)){
            if (password1.length() > 10){
                return password1.length() < 15;
            }
        }
        return false;
    }
}
