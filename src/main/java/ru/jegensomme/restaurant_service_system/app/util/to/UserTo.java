package ru.jegensomme.restaurant_service_system.app.util.to;

import ru.jegensomme.restaurant_service_system.model.Role;

import java.util.Set;

public class UserTo {

    private final Integer id;

    private final String name;

    private final Set<Role> roles;

    public UserTo(Integer id, String name, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    @Override
    public String toString() {
        String rolesStr = "";
        for (Role role : roles) {
            rolesStr += (rolesStr.isEmpty() ? "" : ", ") + role.toStringRus();
        }
        return name + " " + rolesStr;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
