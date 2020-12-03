package ru.jegensomme.restaurant_service_system.model;

public enum Role {
    WAITER,
    MANAGER;

    public String toStringRus() {
        return this == WAITER ? "Официант" : "Менеджер";
    }
}
