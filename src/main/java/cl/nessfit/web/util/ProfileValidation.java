package cl.nessfit.web.util;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidation {
    /**
     *Method that check if all the data from the profile form is valid       
     * @param userService the service to obtain the user's data from the database 
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email
     * @return a list of boolean values 
     */
    public static boolean[] isValidProfile(UserServiceInterface userService, User user, String firstName, String lastName,
                                           String phone, String email){

        // if all errors[] are true, the system is ok!
        boolean[] errors = {true, true, true, true, true, true};

        if(!validNameLength(firstName)){
            errors[1] = false;
            errors[0] = false;
        }

        if (!validLastNameLength(lastName)){
            errors[2] = false;
            errors[0] = false;
        }

        if (!validPhone(phone)){
            errors[3] = false;
            errors[0] = false;
        }

        if (!existEmail(userService, user, email)){
            errors[4] = false;
            errors[0] = false;
        }

        if (!validEmail(email)){
            errors[5] = false;
            errors[0] = false;
        }

        return errors;
    }
    /**
     * Method that checks if the data to register is valid and the user is new.
     * @param userService the service to obtain the user's data from the database 
     * @param rut user's rut
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email
     * @return list of boolean values, if all errors[] are true, the system is ok!
     */
    public static boolean[] isValidRegister(UserServiceInterface userService, String rut, String firstName, String lastName,
                                           String phone, String email){

        // if all errors[] are true, the system is ok!
        boolean[] errors = {true, true, true, true, true, true, true, true};

        if (!existRut(userService, rut)){
            errors[1] = false;
            errors[0] = false;
        }

        if (!validRut(rut)){
            errors[2] = false;
            errors[0] = false;
        }

        if(!validNameLength(firstName)){
            errors[3] = false;
            errors[0] = false;
        }

        if (!validLastNameLength(lastName)){
            errors[4] = false;
            errors[0] = false;
        }

        if (!existEmail(userService, null, email)){
            errors[5] = false;
            errors[0] = false;
        }

        if (!validEmail(email)){
            errors[6] = false;
            errors[0] = false;
        }

        if (!validPhone(phone)){
            errors[7] = false;
            errors[0] = false;
        }

        return errors;
    }

    /**
     * Method that validates if the new name has a valid length.
     * @param firstName Corresponds to the new name for the user.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validNameLength(String firstName) {
        String[] nameArray = firstName.split("\\s+");
        for (String name : nameArray) {
            if (name.length() < 3 || name.length() > 200) { return false; }
        }
        return true;
    }

    /**
     * Method that validates if the new user last name has a valid length.
     * @param lastName Corresponds to the new last name for the user.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validLastNameLength(String lastName) {
        String[] nameArray = lastName.split("\\s+");
        for (String name : nameArray) {
            if (name.length() < 3 || name.length() > 200 ) { return false; }
        }
        return true;
    }

    /**
     * Method that is responsible for validating if the entered phone number is valid and long.
     * @param phone Corresponds to the new phone number.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validPhone(String phone) {
        try {
            if (!phone.equals("")) {
                long phoneLong = Long.parseLong(phone);
                if (phoneLong < 0) { return false; }
                return phone.length() <= 16 && phone.length() >= 11;
            }
            return false;
        } catch (Exception e) { return false; }
    }

    /**
     * Method that is responsible for validating if the email has the correct format.
     * @param email New email for the user.
     * @return "True" if the email is valid and "False" if not.
     */
    public static boolean validEmail(String email) {

        //validate máximo length
        if (email.length() > 200) { return false; }

        Pattern pat = Pattern.compile("([a-zA-Z0-9.]+@[a-zA-Z0-9.]+)");
        Matcher matcher = pat.matcher(email);
        return matcher.matches();
    }

    /**
     * Method that is responsible for validating if the email exists in the system.
     * @param email New email for the user.
     * @return "True" if the email is valid and "False" if not.
     */
    public static boolean existEmail(UserServiceInterface userService, User loggedUser, String email) {
        // If the new email is the same that user email.
        if (loggedUser != null) { if (loggedUser.getEmail().equals(email)) { return true; } }

        // We check if the email currently exists in the system.
        List<User> userList = userService.getUsers();
        for (User user : userList) {
            // If the email exists in the system, then we return false.
            if (user.getEmail().equals(email)) { return false; }
        }
        return true;
    }

    /**
     * Method that checks if the rut is valid
     * @param rut user's rut
     * @return true if rut is valid, otherwise false
     */
    public static boolean validRut(String rut) {
        Pattern pattern = Pattern.compile("^[0-9]{8}[0-9K]$");
        Matcher matcher = pattern.matcher(rut);
        if (matcher.matches()) {
            String dv = calculateDV(rut);
            return rut.charAt(rut.length() - 1) == dv.charAt(0) &&  rut.length() <= 15 && rut.length() >= 8;
        }

        return false;
    }

    /**
     * Method that is responsible for validating if the rut exists in the system.
     * @param rut New rut for the user.
     * @return "True" if the rut is valid and "False" if not.
     */
    public static boolean existRut(UserServiceInterface userService, String rut) {
        // We check if the rut currently exists in the system.
        List<User> userList = userService.getUsers();
        for (User user : userList) {
            // If the rut exists in the system, then we return false.
            if (user.getRut().equals(rut)) { return false; }
        }
        return true;
    }
    /**
     * Calculates the verificator digit given a rut 
     * @param rut user's rut
     * @return string of verificator digit  
     */
    public static String calculateDV(String rut) {
        String rutNumeric = rut.substring(0, rut.length() - 1);

        int M = 0, S = 1, T = Integer.parseInt(rutNumeric);
        for (; T != 0; T = (int) Math.floor(T /= 10))
            S = (S + T % 10 * (9 - M ++ % 6)) % 11;
        return ( S > 0 ) ? String.valueOf(S - 1) : "K";
    }

    /**
     * Method that validate the length of the name when it has spaces between words
     * @param name or last name from the user
     * @return the name formatted without spaces in a string data type
     */
    public static String newNamesEdit(String name) {
        String[] array = name.split("\\s+");
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            newName.append(array[i]);
            if (i != array.length - 1) { newName.append(" "); }
        }
        return newName.toString();
    }
}