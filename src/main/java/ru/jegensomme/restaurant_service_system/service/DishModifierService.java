package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.DishModifierRepository;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DishModifierService {

    private DishModifierRepository repository;
    private UserRepository userRepository;

    @Autowired
    public DishModifierService(DishModifierRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DishModifier create(DishModifier dishModifier, int userId) {
        checkAccess(userId);
        Assert.notNull(dishModifier, "dishModifier must not be null");
        return repository.save(dishModifier);
    }

    @Transactional
    public void delete(int id, int userId) {
        checkAccess(userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(DishModifier dishModifier, int userId) {
        checkAccess(userId);
        Assert.notNull(dishModifier, "dishModifier must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(dishModifier), dishModifier.id());
    }

    public DishModifier get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<DishModifier> getAll() {
        return repository.getAll();
    }

    private void checkAccess(int userId) {
        User user = ValidationUtil.checkNotFoundWithId(userRepository.get(userId), userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }
}
