package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Order create(Order order, int userId) {
        Assert.notNull(order, "order must not be null");
        return repository.save(order, userId);
    }

    @Transactional
    public void delete(int id, int managerId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, managerId), id);
    }

    public Order get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    public void update(Order order, int waiterId) {
        Assert.notNull(order, "order must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(order, waiterId), order.id());
    }

    public List<Order> getAll() {
        return repository.getAll();
    }

    public List<Order> getAllByWaiter(int waiterId) {
        return repository.getAllByWaiter(waiterId);
    }
}
