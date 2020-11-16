package ru.jegensomme.restaurant_service_system.repository.jpa;

import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;

import java.util.List;

public class JpaOrderRepository implements OrderRepository {

    @Override
    public Order save(Order order, int waiterId) {
        return null;
    }

    @Override
    public boolean delete(int id, int managerId) {
        return false;
    }

    @Override
    public Order get(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getAllByWaiter(int waiterId) {
        return null;
    }
}
