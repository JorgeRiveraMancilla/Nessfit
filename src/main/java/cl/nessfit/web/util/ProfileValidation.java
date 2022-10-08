package cl.nessfit.web.util;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidation {

    public static boolean[] isValid(UserServiceInterface userService, String firstName, String lastName,
                              String phone, String email){

        // if all errors are false, the system is ok!
        boolean[] errors = {false, false, false, false, false, false};

        if(!nameLength(firstName)){
             errors[1] = true;
             errors[0] = true;
        }

        if (!lastNameLength(lastName)){
            errors[2] = true;
            errors[0] = true;
        }

        if (!phoneValidator(phone)){
            errors[3] = true;
            errors[0] = true;
        }

        if (!existEmail(userService, email)){
            errors[4] = true;
            errors[0] = true;
        }

        if (!emailValidator(email)){
            errors[5] = true;
            errors[0] = true;
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

    public static boolean nameLength(String name){
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

    public static boolean lastNameLength(String lastName){
        if (lastName.equals("")){ return false; }
        return lastName.length() >= 3;
    }

    /**
     * Method that is responsible for validating if the entered phone number is valid and long.
     * @param phone Corresponds to the new phone number.
     * @return "True" if is valid and "False" if not.
     */
    public static boolean phoneValidator(String phone){
        try {
            if (!phone.equals("")){
                long phoneLong = Long.parseLong(phone);
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
    public static boolean emailValidator(String email) {
        // We verify that the email has the correct format.
        if (email.length() < 2){ return false; }
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
