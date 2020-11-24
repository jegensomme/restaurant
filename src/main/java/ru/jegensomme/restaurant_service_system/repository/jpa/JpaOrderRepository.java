package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Order save(Order order, int userId) {
        order.setUser(em.find(User.class, userId));
        if (order.isNew()) {
            em.persist(order);
            return order;
        } else {
            return em.merge(order);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Order.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
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
    public List<Order> getAllByUser(int userId) {
        return null;
    }
}
