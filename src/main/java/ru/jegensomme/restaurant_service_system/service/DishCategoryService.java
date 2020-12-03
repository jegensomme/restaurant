package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.repository.DishCategoryRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishCategoryService {

    private final DishCategoryRepository repository;

    @Autowired
    public DishCategoryService(DishCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict(value = "dish_categories")
    public DishCategory create(DishCategory dishCategory) throws CreateException {
        Assert.notNull(dishCategory, "dishCategory must not be null");
        return ValidationUtil.checkCreated(repository.save(dishCategory), DishCategory.class);
    }

    @Transactional
    @CacheEvict(value = "dish_categories")
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    @CacheEvict(value = "dish_categories")
    public void update(DishCategory dishCategory) throws NotFoundException {
        Assert.notNull(dishCategory, "dishCategory must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dishCategory), dishCategory.id());
    }

    public DishCategory get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable(value = "dish_categories")
    public List<DishCategory> getAll() {
        return repository.getAll();
    }

    @Cacheable(value = "dish_categories", key = "#categoryId")
    public List<DishCategory> getAllByCategory(int categoryId) {
        return repository.getAllByCategory(categoryId);
    }

    @Cacheable(value = "dish_categories")
    public List<DishCategory> getAllTop() {
        return repository.getAllTop();
    }

}
