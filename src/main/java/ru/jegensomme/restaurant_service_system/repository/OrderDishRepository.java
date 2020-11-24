package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.OrderDish;

import java.util.List;

public interface OrderDishRepository {

    OrderDish save(OrderDish orderDish);

    boolean delete(int id);

    OrderDish get(int id);

    List<OrderDish> getAll(int orderId);

}
