package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.DishRepository;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishService {

    private DishRepository repository;
    private UserRepository userRepository;

    @Autowired
    public DishService(DishRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Dish create(Dish dish, int userId) {
        checkAccess(userId);
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @Transactional
    public void delete(int id, int userId) {
        checkAccess(userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(Dish dish, int userId) {
        checkAccess(userId);
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dish), dish.id());
    }

    public Dish get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<Dish> getAll() {
        return repository.getAll();
    }

    public List<Dish> getAllByCategory(int categoryId) {
        return repository.getAllByCategory(categoryId);
    }

    private void checkAccess(int userId) {
        User user = ValidationUtil.checkNotFoundWithId(userRepository.get(userId), userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }
}

