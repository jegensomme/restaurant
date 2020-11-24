package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.OrderDishRepository;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderDishService {

    private OrderDishRepository repository;
    private UserRepository userRepository;

    @Autowired
    public OrderDishService(OrderDishRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderDish create(OrderDish orderDish, int userId) {
        if (orderDish.getOrder().getUser().id() != userId) {
            checkAccess(userId);
        }
        Assert.notNull(orderDish, "orderDish must not be null");
        return repository.save(orderDish);
    }

    @Transactional
    public void delete(int id, int userId) {
        checkAccess(userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(OrderDish orderDish, int userId) {
        checkAccess(userId);
        Assert.notNull(orderDish, "orderDish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(orderDish), orderDish.id());
    }

    public OrderDish get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<OrderDish> getAll(int orderId) {
        return repository.getAll(orderId);
    }

    private void checkAccess(int userId) {
        User user = ValidationUtil.checkNotFoundWithId(userRepository.get(userId), userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }

}
