package ru.jegensomme.restaurant_service_system.app.util.to.util;

import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.app.util.to.UserTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserUtil {

    private UserUtil() {
    }

    public static List<UserTo> getTos(Collection<User> users) {
        return filterByPredicate(users, user -> true);
    }

    public static List<UserTo> filterByPredicate(Collection<User> users, Predicate<User> filter) {
        return users.stream()
                .filter(filter)
                .map(UserUtil::createTo)
                .collect(Collectors.toList());
    }

    public static UserTo createTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getRoles());
    }
}
