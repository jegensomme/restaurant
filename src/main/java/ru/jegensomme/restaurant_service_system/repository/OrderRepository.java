package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository {

    Order save(Order order, int waiterId);

    boolean delete(int id, int managerId);

    Order get(int id);

    List<Order> getAll();

    List<Order> getAllByWaiter(int waiterId);

    /*
    List<Order> getAllByDate(LocalDate date);

    List<Order> getAllByWaiter(int id);

    List<Order> getAllByWaiterByDate(int id, LocalDate date);
    */
}
