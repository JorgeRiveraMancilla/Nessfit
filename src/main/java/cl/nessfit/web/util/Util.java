package cl.nessfit.web.util;

import cl.nessfit.web.model.User;

import java.util.List;

public class Util {

    /**
     * Validate if a string has a valid length.
     * @param parameter String to validate.
     * @param min Min value.
     * @param max Max value.
     * @return "True" if is valid, "False" if not.
     */
    protected static boolean validSize(String parameter, int min, int max) {
        return min <= parameter.length() && parameter.length() <= max;
    }

    /**
     * Compare if the two parameters are equals.
     * @param parameter1 String one.
     * @param parameter2 String two.
     * @return "True" if are equals, and "False" if not.
     */
    protected static boolean areEquals(String parameter1, String parameter2){
        return parameter1.equals(parameter2);
    }

    /**
     * Validate if the string is a number.
     * @param parameter String to validate.
     * @return "True" if is a number or "False" if not.
     */
    protected static boolean tryParseLong(String parameter) {
        try {
            Long.parseLong(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<User> joinList(List<User> list1, List<User> list2){
        list1.addAll(list2);
        return list1;
    }

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
}
