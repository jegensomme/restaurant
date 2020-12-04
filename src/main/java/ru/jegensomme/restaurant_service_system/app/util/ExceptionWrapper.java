package ru.jegensomme.restaurant_service_system.app.util;

import ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity;

import java.util.function.Supplier;

public class ExceptionWrapper {

    private ExceptionWrapper() {
    }

    public static <T extends AbstractBaseEntity> T getEntityOrNull(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }
}
