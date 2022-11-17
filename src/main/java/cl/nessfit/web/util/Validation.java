package cl.nessfit.web.util;

import cl.nessfit.web.model.Installation;
import cl.nessfit.web.model.User;
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
     * @return String array with all error messages in the editedUser.
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
            errors[3] = actualUser.getEmail().equals(editedUser.getEmail()) ?
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

    public static String validPassword(String password1, String password2){
        // Este es prioridad.
        if (!areEquals(password1, password2)) { return "Las contraseñas no son iguales"; }
        if (!validSize(password1, 10, 15)) {
            return "El largo de la contraseña debe estar entre 10 y 15 caracteres";
        }
        return "";
    }

    //endregion


    private static String validFormatInstallationName(InstallationServiceInterface installationService, String name){
        name = name.strip();
        if (name.equals("")) { return "Campo Obligatorio"; }
        if (name.length() > 200) { return "Largo inválido"; }
        if (installationService.searchByName(name) != null) { return "Esta instalación ya existe"; }
        return "";
    }

    private static String validFormatName(String name){
        name = name.strip();
        if (name.equals("")) { return "Campo Obligatorio"; }
        if (name.length() > 200) { return "Largo inválido"; }
        Pattern pattern = Pattern.compile("^([A-zÀ-ú]{3,}\\s)*[A-zÀ-ú]{3,}$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()){ return ""; }
        return "Los nombres o apellidos deben tener más de 2 caracteres";
    }

    private static String validFormatPhone(String phone){
        phone = phone.strip();
        if (phone.equals("")) { return "Campo Obligatorio"; }
        if (!tryParseLong(phone)) { return "Solo se aceptan valores numéricos"; }
        if (phone.length() > 200) { return "Largo inválido"; }
        if (!validSize(phone, 11, 16)) { return "El teléfono móvil ingresado no es válido"; }
        return "";
    }

    private static String validFormatRut(String rut){
        if (rut.strip().equals("")) { return "Campo Obligatorio"; }
        if (rut.length() < 9) { return "No se permiten RUT menores a 10.000.000-0"; }
        if (ProfileValidation.validRut(rut)) { return ""; }
        return "RUT inválido";
    }

    private static String validFormatEmail(String email){
        if (email.strip().equals("")) { return "Campo Obligatorio"; }
        if (ProfileValidation.validEmail(email)) { return ""; }
        return "Su correo electrónico no es válido";
    }

    private static String validFormatAddress(String address){
        address = address.strip();
        if (address.equals("")) { return "Campo obligatorio"; }
        if (address.length() > 200) { return "Largo inválido"; }
        return "";
    }

    private static String validFormatRentalCost(String rentalCost){
        if (!tryParseLong(rentalCost)) { return "Formato inválido"; }
        rentalCost = rentalCost.strip();
        if (rentalCost.equals("")) { return "Campo obligatorio"; }
        if (!(1000 <= Integer.parseInt(rentalCost))) {
            return "El costo mínimo de arriendo debe ser $1.000 (1000)";
        }
        if (!(100000 >= Integer.parseInt(rentalCost))) {
            return "El costo máximo de arriendo debe ser $100.000 (100000)";
        }
        return "";
    }

    private static String existsRutEmail(UserServiceInterface userService, String parameter, String type){
        switch (type){
            case "rut":
                if (ProfileValidation.existRut(userService, parameter)){
                    return "Rut ingresado ya existe, intente iniciar sesión";
                }
            case "email":
                if (ProfileValidation.existEmail(userService, parameter)){
                    return "El correo electrónico ingresado ya existe";
                }
            default:
                return "";
        }
    }

    private static boolean validSize(String parameter, int min, int max) {
        return min <= parameter.length() && parameter.length() <= max;
    }

    private static boolean areEquals(String parameter1, String parameter2){
        return parameter1.equals(parameter2);
    }

    private static boolean tryParseLong(String parameter) {
        try {
            Long.parseLong(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
