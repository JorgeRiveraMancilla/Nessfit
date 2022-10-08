package cl.nessfit.web.util;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import java.util.List;

public class ProfileValidation {

    /**
     *
     * @param userService
     * @param firstName
     * @param lastName
     * @param phone
     * @param email
     * @return
     */
    public static boolean[] isValid(UserServiceInterface userService, String firstName, String lastName,
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

        if (!existEmail(userService, email)){
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
     * Method that validates if the new name has a valid length.
     * @param firstName Corresponds to the new name for the user.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validNameLength(String firstName){
        String[] nameArray = firstName.split("\\s+");
        for (String name : nameArray) {
            if (name.length() < 3) { return false; }
        }
        return true;
    }

    /**
     * Method that validates if the new user last name has a valid length.
     * @param lastName Corresponds to the new last name for the user.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validLastNameLength(String lastName){
        String[] nameArray = lastName.split("\\s+");
        for (String name : nameArray) {
            if (name.length() < 3) { return false; }
        }
        return true;
    }

    /**
     * Method that is responsible for validating if the entered phone number is valid and long.
     * @param phone Corresponds to the new phone number.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean validPhone(String phone){
        try {
            if (!phone.equals("")){
                Long.parseLong(phone);
                return phone.length() <= 16 && phone.length() >= 11;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Method that is responsible for validating if the email has the correct format.
     * @param email New email for the user.
     * @return "True" if the email is valid and "False" if not.
     */
    public static boolean validEmail(String email) {
        // We verify that the email has "@".
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@'){
                return true;
            }
        }
        return false;
    }

    /**
     * Method that is responsible for validating if the email exists in the system.
     * @param email New email for the user.
     * @return "True" if the email is valid and "False" if not.
     */
    public static boolean existEmail(UserServiceInterface userService, String email){
        // We check if the email currently exists in the system.
        List<User> userList = userService.getUsers();
        for (User user : userList) {
            // If the email exists in the system, then we return false.
            if (user.getEmail().equals(email)){ return false; }
        }
        return true;
    }

    /**
     * Method that is responsible for validating if the rut exists in the system.
     * @param rut New rut for the user.
     * @return "True" if the rut is valid and "False" if not.
     */
    public static boolean existRut(UserServiceInterface userService, String rut){
        // We check if the rut currently exists in the system.
        List<User> userList = userService.getUsers();
        for (User user : userList) {
            // If the rut exists in the system, then we return false.
            if (user.getRut().equals(rut)){ return false; }
        }
        return true;
    }

}
