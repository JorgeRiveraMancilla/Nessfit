package cl.nessfit.web.util;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
import net.bytebuddy.asm.Advice;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    //region PUBLIC VALIDATIONS

    /**
     * Validates changes made to a user's data.
     * @param userService UserService instance.
     * @param actualUser Original user.
     * @param editedUser Original user with changed attributes.
     * @return String array with all error messages for edited user controllers.
     */
    public static String[] editProfileValidation(UserServiceInterface userService, User actualUser, User editedUser){

        String[] errors = new String[5];
        // FirstName
        errors[1] = validFormatName(editedUser.getFirstName());
        // LastName
        errors[2] = validFormatName(editedUser.getLastName());
        // Email
        errors[3] = validFormatEmail(editedUser.getEmail());
        if (errors[3].equals("")){
            errors[3] = actualUser.getEmail().equalsIgnoreCase(editedUser.getEmail()) ?
                    "" : existsRutEmail(userService, editedUser.getEmail(), "email");
        }
        // Phone
        errors[4] = validFormatPhone(editedUser.getPhone());

        for (int i = 1; i < 5; i++) {
            if (!errors[i].equals("")){
                errors[0] = "false";
                return errors;
            }
        }
        errors[0] = "true";
        return errors;
    }

    /**
     * Validates all parameters for all register user controllers.
     * @param userService UserService instance.
     * @param user New user.
     * @return String array with all error messages for register user controllers.
     */
    public static String[] registerUserValidation(UserServiceInterface userService, User user){
        String[] errors = new String[6];
        // RUT
        errors[1] = validFormatRut(user.getRut());
        if (errors[1].equals("")) { errors[1] = existsRutEmail(userService, user.getRut(), "rut"); }
        // FirstName
        errors[2] = validFormatName(user.getFirstName());
        // LastName
        errors[3] = validFormatName(user.getLastName());
        // Email
        errors[4] = validFormatEmail(user.getEmail());
        if (errors[4].equals("")){ errors[4] = existsRutEmail(userService, user.getEmail(), "email"); }
        // Phone
        errors[5] = validFormatPhone(user.getPhone());

        for (int i = 1; i < 6; i++) {
            if (!errors[i].equals("")){
                errors[0] = "false";
                return errors;
            }
        }
        errors[0] = "true";
        return errors;
    }

    /**
     * Validates changes made to a installation's data.
     * @param installation Original installation with changed attributes.
     * @return String array with all error messages for the edited installation controller.
     */
    public static String[] editInstallationValidation(Installation installation){

        String[] errors = new String[3];

        errors[1] = validFormatAddress(installation.getAddress());
        errors[2] = validFormatRentalCost(installation.getRentalCost());

        for (int i = 1; i < 3; i++) {
            if (!errors[i].equals("")){
                errors[0] = "false";
                return errors;
            }
        }
        errors[0] = "true";
        return errors;
    }

    /**
     * Validates all parameters for the register installation controller.
     * @param installationService InstallationService instance.
     * @param installation New installation.
     * @return String array with all error messages for the register installation controller.
     */
    public static String[] registerInstallationValidation(InstallationServiceInterface installationService, Installation installation){

        String[] errors = new String[4];

        errors[1] = validFormatInstallationName(installationService, installation.getName());
        errors[2] = validFormatAddress(installation.getAddress());
        errors[3] = validFormatRentalCost(installation.getRentalCost());

        for (int i = 1; i < 4; i++) {
            if (!errors[i].equals("")){
                errors[0] = "false";
                return errors;
            }
        }
        errors[0] = "true";
        return errors;
    }

    /**
     * Validate the password for the user.
     * @param password1 New password.
     * @param password2 New password repeated.
     * @return String array with all error messages for the change password controller.
     */
    public static String validPassword(String password1, String password2){
        if (!areEquals(password1, password2)) { return "Las contraseñas no son iguales"; }
        if (!validSize(password1, 10, 15)) {
            return "El largo de la contraseña debe estar entre 10 y 15 caracteres";
        }
        return "";
    }

    /**
     * Validate if the category exists.
     * @param categoryService CategoryService instance.
     * @param category Category name.
     * @return "True" if the category exists or "False" if not.
      */
    public static boolean existsCategory(CategoryServiceInterface categoryService, String category) {
        return categoryService.exists(category);
    }

    //endregion

    /**
     * Validate if the format of installation name has an error.
     * @param installationService InstallationService instance.
     * @param name Installation name.
     * @return Error for the name.
     */
    private static String validFormatInstallationName(InstallationServiceInterface installationService, String name){
        name = name.strip();
        if (name.equals("")) { return "Campo Obligatorio"; }
        if (name.length() > 200) { return "Largo inválido"; }
        if (installationService.searchByName(name) != null) { return "Esta instalación ya existe"; }
        return "";
    }

    /**
     * Validate if the format of user first or last name has an error.
     * @param name First or last name of the user.
     * @return Error for the name.
     */
    private static String validFormatName(String name){
        name = name.strip();
        if (name.equals("")) { return "Campo obligatorio"; }
        if (name.length() > 200) { return "Largo inválido"; }
        Pattern pattern = Pattern.compile("^([A-zÀ-ú]{3,}\\s)*[A-zÀ-ú]{3,}$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()){ return ""; }
        return "Los nombres o apellidos deben tener más de 2 caracteres";
    }

    /**
     * Validate if the format of user phone has an error.
     * @param phone Phone of the user.
     * @return Error for the phone.
     */
    private static String validFormatPhone(String phone){
        phone = phone.strip();
        if (phone.equals("")) { return "Campo obligatorio"; }
        if (phone.length() > 200) { return "Largo inválido"; }
        if (!validSize(phone, 11, 16)) { return "El teléfono móvil ingresado no es válido"; }
        if (!tryParseLong(phone)) { return "Solo se aceptan valores numéricos"; }
        return "";
    }

    /**
     * Validate if the format of user rut has an error.
     * @param rut Rut of the user.
     * @return Error for the rut.
     */
    private static String validFormatRut(String rut){
        rut = rut.strip();
        if (rut.equals("")) { return "Campo obligatorio"; }
        if (!validRut(rut)) { return "RUT inválido"; }
        if (rut.length() < 9) { return "No se permiten RUT menores a 10.000.000-0"; }
        return "";
    }

    /**
     * Validate if the format of user email has an error.
     * @param email Email of the user.
     * @return Error for the email.
     */
    private static String validFormatEmail(String email){
        email = email.toLowerCase().strip();
        if (email.equals("")) { return "Campo obligatorio"; }
        if (validEmail(email)) { return ""; }
        return "Su correo electrónico no es válido";
    }

    /**
     * Validate if the format of installation address has an error.
     * @param address Installation address.
     * @return Error for the address.
     */
    private static String validFormatAddress(String address){
        address = address.strip();
        if (address.equals("")) { return "Campo obligatorio"; }
        if (address.length() > 200) { return "Largo inválido"; }
        return "";
    }

    /**
     * Validate if the format of installation rental cost has an error.
     * @param rentalCost Installation rental cost.
     * @return Error for the rental cost.
     */
    private static String validFormatRentalCost(String rentalCost){
        rentalCost = rentalCost.strip();
        if (!tryParseLong(rentalCost)) { return "Formato inválido"; }
        if (rentalCost.equals("")) { return "Campo obligatorio"; }
        if (!(1000 <= Long.parseLong(rentalCost))) {
            return "El costo mínimo de arriendo debe ser $1.000 (1000)";
        }
        if (!(1000000000 >= Long.parseLong(rentalCost))) {
            return "El costo máximo de arriendo debe ser $1.000.000.000 (1000000000)";
        }
        return "";
    }

    /**
     * Check if the rut or email exist in the system.
     * @param userService UserService instance.
     * @param parameter Rut or email.
     * @param type "rut" if we want to validate a rut, or "email" if we wat to validate an email.
     * @return Exists error rut or email.
     */
    private static String existsRutEmail(UserServiceInterface userService, String parameter, String type){
        switch (type){
            case "rut":
                if (existRut(userService, parameter)){
                    return "Rut ingresado ya existe, intente iniciar sesión";
                }
            case "email":
                if (existEmail(userService, parameter.toLowerCase())){
                    return "El correo electrónico ingresado ya existe";
                }
            default:
                return "";
        }
    }

    /**
     * Validate if a string has a valid length.
     * @param parameter String to validate.
     * @param min Min value.
     * @param max Max value.
     * @return "True" if is valid, "False" if not.
     */
    private static boolean validSize(String parameter, int min, int max) {
        return min <= parameter.length() && parameter.length() <= max;
    }

    /**
     * Compare if the two parameters are equals.
     * @param parameter1 String one.
     * @param parameter2 String two.
     * @return "True" if are equals, and "False" if not.
     */
    private static boolean areEquals(String parameter1, String parameter2){
        return parameter1.equals(parameter2);
    }

    /**
     * Validate if the string is a number.
     * @param parameter String to validate.
     * @return "True" if is a number or "False" if not.
     */
    private static boolean tryParseLong(String parameter) {
        try {
            Long.parseLong(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Performs a merge of two lists.
     * @param list1 User list1.
     * @param list2 User list2.
     * @return Combined lists.
     */
    public static List<User> joinList(List<User> list1, List<User> list2){
        list1.addAll(list2);
        return list1;
    }

    /**
     * Converts the first letter to uppercase.
     * @param inputString String to capitalize.
     * @return String capitalized.
     */
    public static String capitalize(String inputString) {
        inputString = inputString.toLowerCase();
        StringBuilder newString = new StringBuilder();
        String[] words = inputString.split(" ");

        // for each word in words
        for (String word : words) {
            //if word are equals to "", we ignore it
            if (!word.equals("")){
                // if the value of j are equal to 0, then the character is now to uppercase
                for (int j = 0; j < word.length(); j++) {
                    if (j == 0) {
                        newString.append(Character.toUpperCase(word.charAt(j)));
                    }else{
                        newString.append(word.charAt(j));
                    }
                }
                // Space character
                newString.append(" ");
            }
        }
        return newString.toString().strip();
    }

    /**
     * Method that indicates if a rut exists in the database or not.
     * @param userService UserService instance.
     * @param rut User rut to verify.
     * @return "True" if the rut exist or "False" if not.
     */
    private static boolean existRut(UserServiceInterface userService, String rut){
        return userService.searchByRut(rut) != null;
    }

    /**
     * Method that indicates if an email exists in the database or not.
     * @param userService UserService instance.
     * @param email User email to verify.
     * @return "True" if the email exist or "False" if not.
     */
    private static boolean existEmail(UserServiceInterface userService, String email){
        return userService.searchByEmail(email) != null;
    }

    /**
     * Method that checks if the rut is valid.
     * @param rut user's rut.
     * @return true if rut is valid, otherwise false.
     */
    private static boolean validRut(String rut){
        Pattern pattern = Pattern.compile("^([1-9][0-9]{6,13}[0-9K])$");
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
    private static boolean validEmail(String email){
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

    /**
     * List dates.
     */
    private static final List<LocalDate> listDates = new ArrayList<LocalDate>();

    /**
     * Method that get an error message from requests.
     * @return Days error message.
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing.
     */
    public static String getDaysMessage(String days) throws ParseException {
        if (days.equals("")) {
            return " Debe seleccionar al menos un día";
        }

        String[] listDays = days.split(",");
        for (String day : listDays) {
            listDates.add(LocalDate.parse(day, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return null;
    }

    /**
     * Method that get the dates from a request.
     * @return Dates from a request.
     */
    public static List<LocalDate> getListDates() {
        return listDates;
    }


}
