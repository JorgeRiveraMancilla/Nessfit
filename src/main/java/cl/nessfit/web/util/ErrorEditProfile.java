package cl.nessfit.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cl.nessfit.web.model.User;
import cl.nessfit.web.service.UserServiceInterface;

public class ErrorEditProfile {

	
	public static String[] EditUserError(UserServiceInterface userService, User actualUser, User editedUser) {
		
		String[] errors = new String[5];
		//error[0] is false if there are no errors
		errors[1] = nameError(editedUser.getFirstName());
		errors[2] = nameError(editedUser.getLastName()); 
		errors[3] = emailError(userService, actualUser, editedUser.getEmail());
		errors[4] = nameError(editedUser.getFirstName());
		
		
		for(int i = 1; i < errors.length  ;i++) {
			if ( !errors[i].equals("") ){
				errors[0] = "false";
				return errors;
			}
		}
		errors[0] = "true";
		return errors;
				
	}

	private static String nameError(String firstName) {
		if (firstName.strip().equals("")) {
			return "Campo obligatorio";
		}
		if (firstName.length()>200) {
			return "largo inválido";
		}
		Pattern pattern = Pattern.compile("^([A-zÀ-ú]{3,}\\s)*[A-zÀ-ú]{3,}$");
        Matcher matcher = pattern.matcher(firstName);
        if (!matcher.matches()) {
        	return "Los nombres deben tener más de 2 caracteres y contener solo letras";	
        }
        
        
		return "";
	}
	
	private static String emailError(UserServiceInterface userService, User actualUser, String email) {
		if (email.strip().equals("")) {
			return "Campo obligatorio";
		}
		if (email.length()>200) {
			return "largo inválido";
		}
		Pattern pattern = Pattern.compile("^([A-z0-9.]+@[A-z0-9.]+)$"); 
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
        	return "Su correo electrónico no es válido";	
        }
        if ( !actualUser.getEmail().equals(email)) {
        	if(userService.searchByEmail(email) != null) {
        		return " El correo electrónico ya existe en el sistema. ";
        	}	
        }
		return "";
		
	}
	
}
