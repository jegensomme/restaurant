package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private OrderRepository repository;
    private UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order create(Order order, int userId) {
        Assert.notNull(order, "order must not be null");
        return repository.save(order, userId);
    }

    @Transactional
    public void delete(int id, int userId) {
        checkAccess(userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(Order order, int userId) {
        if (order.getUser().id() != userId) {
            checkAccess(userId);
        }
        Assert.notNull(order, "order must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(order, userId), order.id());
    }

    public Order get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<Order> getAll() {
        return repository.getAll();
    }

    public List<Order> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    private void checkAccess(int userId) {
        User user = ValidationUtil.checkNotFoundWithId(userRepository.get(userId), userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }
}
