package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.repository.DishRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict(value = "dishes")
    public Dish create(Dish dish) throws CreateException {
        Assert.notNull(dish, "dish must not be null");
        return ValidationUtil.checkCreated(repository.save(dish), Dish.class);
    }

    @Transactional
    @CacheEvict(value = "dishes")
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    @CacheEvict(value = "dishes")
    public void update(Dish dish) throws NotFoundException {
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dish), dish.id());
    }

    public Dish get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable(value = "dishes")
    public List<Dish> getAll() {
        return repository.getAll();
    }

    @Cacheable(value = "dishes", key = "#categoryId")
    public List<Dish> getAllByCategory(int categoryId) {
        return repository.getAllByCategory(categoryId);
    }

    @Cacheable(value = "dishes")
    public List<Dish> getAllTop() {
        return repository.getAllTop();
    }

}

