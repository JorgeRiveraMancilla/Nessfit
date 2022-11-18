package cl.nessfit.web.util;

import cl.nessfit.web.service.UserServiceInterface;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidation {

    /**
     * Method that indicates if a rut exists in the database or not.
     * @param userService UserService instance.
     * @param rut User rut to verify.
     * @return "True" if the rut exist or "False" if not.
     */
    protected static boolean existRut(UserServiceInterface userService, String rut){
        return userService.searchByRut(rut) != null;
    }

    /**
     * Method that indicates if an email exists in the database or not.
     * @param userService UserService instance.
     * @param email User email to verify.
     * @return "True" if the email exist or "False" if not.
     */
    protected static boolean existEmail(UserServiceInterface userService, String email){
        return userService.searchByEmail(email) != null;
    }

    /**
     * Method that checks if the rut is valid.
     * @param rut user's rut.
     * @return true if rut is valid, otherwise false.
     */
    protected static boolean validRut(String rut){
        Pattern pattern = Pattern.compile("^([1-9][0-9]{7,13}[0-9K])$");
        Matcher matcher = pattern.matcher(rut);
        if (matcher.matches()){
            String dv = calculateDV(rut);
            return rut.charAt(rut.length() - 1) == dv.charAt(0);
        }
        return false;
    }

    /**
     * Method that checks if the email is valid.
     * @param email user's email.
     * @return true if email is valid, otherwise false.
     */
    protected static boolean validEmail(String email){
        Pattern pattern = Pattern.compile("^(([a-z0-9.]{1,70})@([a-z0-9.]{1,70}))$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Calculates the verification digit given a rut.
     * @param rut user's rut.
     * @return string of verification digit.
     */
    private static String calculateDV(String rut) {
        String rutNumeric = rut.substring(0, rut.length() - 1);

        int M = 0, S = 1, T = Integer.parseInt(rutNumeric);
        for (; T != 0; T = (int) Math.floor(T /= 10))
            S = (S + T % 10 * (9 - M ++ % 6)) % 11;
        return ( S > 0 ) ? String.valueOf(S - 1) : "K";
    }
}