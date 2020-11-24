package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.repository.OrderDishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaOrderDishRepository implements OrderDishRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public OrderDish save(OrderDish orderDish) {
        if (orderDish.isNew()) {
            em.persist(orderDish);
            return orderDish;
        } else {
            return em.merge(orderDish);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(OrderDish.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
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
