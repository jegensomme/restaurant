package ru.jegensomme.restaurant_service_system.repository.jpa;

import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;

import java.util.List;

public class JpaUserRepository implements UserRepository {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByKey(String key) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
