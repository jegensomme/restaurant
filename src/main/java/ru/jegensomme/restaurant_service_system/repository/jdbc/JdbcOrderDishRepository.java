package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.repository.OrderDishRepository;

import java.util.List;

@Repository
public class JdbcOrderDishRepository implements OrderDishRepository {

    @Override
    public OrderDish save(OrderDish orderDish) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public OrderDish get(int id) {
        return null;
    }

    @Override
    public List<OrderDish> getAll(int orderId) {
        return null;
    }
}
