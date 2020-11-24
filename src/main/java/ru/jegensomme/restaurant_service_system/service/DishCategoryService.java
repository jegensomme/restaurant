package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.DishCategoryRepository;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishCategoryService {

    private DishCategoryRepository repository;
    private UserRepository userRepository;

    @Autowired
    public DishCategoryService(DishCategoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DishCategory create(DishCategory dishCategory, int userId) {
        checkAccess(userId);
        Assert.notNull(dishCategory, "dishCategory must not be null");
        return repository.save(dishCategory);
    }

    @Transactional
    public void delete(int id, int userId) {
        checkAccess(userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(DishCategory dishCategory, int userId) {
        checkAccess(userId);
        Assert.notNull(dishCategory, "dishCategory must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dishCategory), dishCategory.id());
    }

    public DishCategory get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<DishCategory> getAll() {
        return repository.getAll();
    }

    public List<DishCategory> getAllByCategory(int categoryId) {
        return repository.getAllByCategory(categoryId);
    }

    private void checkAccess(int userId) {
        User user = ValidationUtil.checkNotFoundWithId(userRepository.get(userId), userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }

}
