package ru.jegensomme.restaurant_service_system.app.util;

import ru.jegensomme.restaurant_service_system.model.Role;

import java.util.EnumSet;
import java.util.Set;

public class SecurityUtil {

    private static int id = 1;
    private static Set<Role> roles = EnumSet.of(Role.WAITER);

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static Set<Role> authUserRoles() {
        return roles;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static void setAuthUserRoles(Set<Role> roles) {
        SecurityUtil.roles = roles;
    }
}


