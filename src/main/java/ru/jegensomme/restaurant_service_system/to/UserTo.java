package ru.jegensomme.restaurant_service_system.to;

import ru.jegensomme.restaurant_service_system.model.User;

public class UserTo {

    private final Integer id;

    private final String name;

    public UserTo(User user) {
        this(user.getId(), user.getName());
    }

    public UserTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
