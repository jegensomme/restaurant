package ru.jegensomme.restaurant_service_system.util;

import ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkAccess(boolean denied, int id) {
        if (denied) {
            throw new AccessException("Access denied for user with id=" + id);
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureKeyIsCorrect(String key) {
        if (!(StringUtil.isNumber(key) && key.length() >= 4 && key.length() <= 10)) {
            throw new IllegalArgumentException("invalid key format");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}
