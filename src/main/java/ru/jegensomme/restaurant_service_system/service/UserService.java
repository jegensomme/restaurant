package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.JpaUtil;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;

    private final JpaUtil jpaUtil;

    @Autowired
    public UserService(UserRepository repository, JpaUtil jpaUtil) {
        this.repository = repository;
        this.jpaUtil = jpaUtil;
    }

    public void authUser(Role role) {
        Assert.notNull(role, "role must not be null");
        jpaUtil.authUser(role);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) throws CreateException {
        Assert.notNull(user, "user must not be null");
        return ValidationUtil.checkCreated(repository.save(user), User.class);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(user), user.id());
    }

    @Cacheable(value = "users", key = "#key")
    public User getByKey(String key) throws NotFoundException {
        Assert.notNull(key, "key must be not null");
        return ValidationUtil.checkNotFound(repository.getByKey(key), "key=" + key);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @Cacheable(value = "users", key = "#role")
    public List<User> getAllByRole(Role role) {
        return repository.getAllByRole(role);
    }

    public List<User> getAllByShiftDate(LocalDate date) {
        return repository.getAllByShiftDate(date);
    }
}
