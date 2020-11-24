package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Transactional
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(user), user.id());
    }

    public User getByKey(String key) {
        Assert.notNull(key, "key must be not null");
        return ValidationUtil.checkNotFound(repository.getByKey(key), "key=" + key);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public List<User> getAllByRole(Role role) {
        return repository.getAllByRole(role);
    }
}
