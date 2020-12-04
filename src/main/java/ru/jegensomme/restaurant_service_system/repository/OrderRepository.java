package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order, int userId);

    boolean delete(int id);

    Order get(int id);

    Order getWithContent(int id) ;

    List<Order> getAll();

    List<Order> getAllByUser(int userId);

    List<Order> getAllOpened();

    List<Order> getAllOpenedByUser(int userId);

    List<Order> getAllByUserShift(int userShiftId);
}
