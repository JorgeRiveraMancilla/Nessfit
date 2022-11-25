package cl.nessfit.web.util;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.User;
import cl.nessfit.web.service.CategoryServiceInterface;
import cl.nessfit.web.service.InstallationServiceInterface;
import cl.nessfit.web.service.UserServiceInterface;
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
        if (!Util.areEquals(password1, password2)) { return "Las contraseñas no son iguales"; }
        if (!Util.validSize(password1, 10, 15)) {
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
        if (name.equals("")) { return "Campo Obligatorio"; }
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
        if (phone.equals("")) { return "Campo Obligatorio"; }
        if (phone.length() > 200) { return "Largo inválido"; }
        if (!Util.validSize(phone, 11, 16)) { return "El teléfono móvil ingresado no es válido"; }
        if (!Util.tryParseLong(phone)) { return "Solo se aceptan valores numéricos"; }
        return "";
    }

    /**
     * Validate if the format of user rut has an error.
     * @param rut Rut of the user.
     * @return Error for the rut.
     */
    private static String validFormatRut(String rut){
        rut = rut.strip();
        if (rut.equals("")) { return "Campo Obligatorio"; }
        if (!ProfileValidation.validRut(rut)) { return "RUT inválido"; }
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
        if (email.equals("")) { return "Campo Obligatorio"; }
        if (ProfileValidation.validEmail(email)) { return ""; }
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
        if (!Util.tryParseLong(rentalCost)) { return "Formato inválido"; }
        if (rentalCost.equals("")) { return "Campo obligatorio"; }
        if (!(1000 <= Integer.parseInt(rentalCost))) {
            return "El costo mínimo de arriendo debe ser $1.000 (1000)";
        }
        if (!(1000000000 >= Integer.parseInt(rentalCost))) {
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
                if (ProfileValidation.existRut(userService, parameter)){
                    return "Rut ingresado ya existe, intente iniciar sesión";
                }
            case "email":
                if (ProfileValidation.existEmail(userService, parameter.toLowerCase())){
                    return "El correo electrónico ingresado ya existe";
                }
            default:
                return "";
        }
    }



}
