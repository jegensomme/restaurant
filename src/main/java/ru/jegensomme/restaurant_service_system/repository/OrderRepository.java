package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository {

    Order save(Order order, int userId);

    boolean delete(int id);

    Order get(int id);

    List<Order> getAll();

    List<Order> getAllByUser(int userId);

    List<Order> getAllOpened();

    List<Order> getAllOpenedByUser(int userId);

    List<Order> getAllByUserShiftStatus(int userShiftId, OrderStatus status);
}
