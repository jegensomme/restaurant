package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByKey(String key);

    List<User> getAll();

    List<User> getAllByRole(Role role);

    List<User> getAllByShiftDate(LocalDate date);
}
