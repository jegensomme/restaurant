package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.repository.OrderDishRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderDishService {

    private final OrderDishRepository repository;

    @Autowired
    public OrderDishService(OrderDishRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OrderDish create(OrderDish orderDish) throws CreateException {
        Assert.notNull(orderDish, "orderDish must not be null");
        return ValidationUtil.checkCreated(repository.save(orderDish), OrderDish.class);
    }

    @Transactional
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(OrderDish orderDish) throws NotFoundException {
        Assert.notNull(orderDish, "orderDish must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(orderDish), orderDish.id());
    }

    public OrderDish get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<OrderDish> getAll(int orderId) {
        return repository.getAll(orderId);
    }

}
