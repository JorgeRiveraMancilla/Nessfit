package cl.nessfit.web.util;

public class Util {
    public static boolean isBlank(String parameter) {
        return parameter.strip().equals("");
    }

    public static boolean validSize(String parameter, int min, int max) {
        return min <= parameter.length() && parameter.length() <= max;
    }

    public static boolean tryParseInt(String parameter) {
        try {
            Integer.parseInt(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validMin(int parameter, int min) {
        return min <= parameter;
    }

    public static boolean validMax(int paremeter, int max) {
        return paremeter <= max;
    }
}