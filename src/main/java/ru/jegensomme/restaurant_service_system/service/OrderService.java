package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.repository.OrderDishRepository;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository repository;

    private final OrderDishRepository orderDishRepository;

    @Autowired
    public OrderService(OrderRepository repository, OrderDishRepository orderDishRepository) {
        this.repository = repository;
        this.orderDishRepository = orderDishRepository;
    }

    @Transactional
    public Order create(Order order, int userId) throws CreateException {
        Assert.notNull(order, "order must not be null");
        order = ValidationUtil.checkCreated(repository.save(order, userId), Order.class);
        for (OrderDish dish : order.getDishes()) {
            ValidationUtil.checkCreated(orderDishRepository.save(dish), OrderDish.class);
        }
        return order;
    }

    @Transactional
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(Order order, int userId) throws NotFoundException {
        Assert.notNull(order, "order must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(order, userId), order.id());
    }

    @Transactional
    public void updateWithContent(Order order, int userId) throws NotFoundException {
        update(order, userId);
        for (OrderDish dish : order.getDishes()) {
            ValidationUtil.checkNotFoundWithId(orderDishRepository.save(dish), order.id());
        }
    }

    public Order get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<Order> getAll() {
        return repository.getAll();
    }

    public List<Order> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    public List<Order> getAllOpened() {
        return repository.getAllOpened();
    }

    public List<Order> getAllOpenedByUser(int userId) {
        return repository.getAllOpenedByUser(userId);
    }

    public List<Order> getAllByUserShiftStatus(int userShiftId, OrderStatus status) {
        return repository.getAllByUserShiftStatus(userShiftId, status);
    }

}
