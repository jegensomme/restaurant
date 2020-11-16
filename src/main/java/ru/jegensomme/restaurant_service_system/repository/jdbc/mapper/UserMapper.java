package ru.jegensomme.restaurant_service_system.repository.jdbc.mapper;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class UserMapper {

    public List<User> mapRowSet(SqlRowSet rowSet) {
        if (!rowSet.next()) {
            return null;
        }
        List<User> users = new ArrayList<>();
        boolean isEnd = false;
        while(!isEnd) {
            Integer id  = rowSet.getInt(1);
            String name = rowSet.getString(2);
            String key  = rowSet.getString(3);
            Set<Role> roles = EnumSet.noneOf(Role.class);
            while (!isEnd) {
                if (rowSet.getInt(1) != id) {
                    break;
                }
                roles.add(Role.valueOf(rowSet.getString(4)));
                isEnd = !rowSet.next();
            }
            users.add(new User(id, name, key, roles));
        }
        return users;
    }
}
