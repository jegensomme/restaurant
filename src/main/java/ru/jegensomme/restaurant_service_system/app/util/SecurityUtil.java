package ru.jegensomme.restaurant_service_system.app.util;

import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;

public class SecurityUtil {

    private static Integer id = null;
    private static Role role = null;

    private SecurityUtil() {
    }

    public static Integer authUserId() {
        return id;
    }

    public static Role authUserRole() {
        return role;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static void setAuthUserRole(Role role) {
        SecurityUtil.role = role;
    }

    public static void checkAccess(int userId) throws AccessException {
        if (userId != SecurityUtil.authUserId()) {
            checkManagerAccess();
        }
    }

    public static void checkManagerAccess() throws AccessException {
        ValidationUtil.checkAccess(!(SecurityUtil.authUserRole() == Role.MANAGER), SecurityUtil.authUserId());
    }
}


