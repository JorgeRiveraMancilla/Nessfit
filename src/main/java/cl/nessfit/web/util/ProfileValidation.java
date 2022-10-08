package cl.nessfit.web.util;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import java.util.List;

public class ProfileValidation {

    public static boolean[] isValid(UserServiceInterface userService, String firstName, String lastName,
                              String phone, String email){

        // if all errors are true, the system is ok!
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
     * Method that validates if the user's current name is equal to the new name.
     * @param user Corresponds to the user to edit.
     * @param name Corresponds to the new name for the user.
     * @return "True" if are equals and "False" if not.
     */
    public static boolean areNameEquals(User user, String name){
        return user.getFirstName().equals(name);
    }

    public static boolean validNameLength(String name){
        if (name.equals("")){ return false; }
        return name.length() >= 3;
    }

    /**
     * Method that validates if the user's current last name is equal to the new last name.
     * @param user Corresponds to the user to edit.
     * @param lastName Corresponds to the new last name for the user.
     * @return "True" if are equals and "False" if not.
     */
    public static boolean areLastNameEquals(User user, String lastName){
        return user.getLastName().equals(lastName);
    }

    public static boolean validLastNameLength(String lastName){
        if (lastName.equals("")){ return false; }
        return lastName.length() >= 3;
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

}
