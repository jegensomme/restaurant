package ru.jegensomme.restaurant_service_system.util;

public class StringUtil {

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
