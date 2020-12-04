package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
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
    @SuppressWarnings("unchecked")
    public OrderDish get(int id) {
        List<OrderDish> result = em.createNativeQuery("select * from order_dishes od" +
                " left join dishes d on d.id = od.dish_id" +
                " left join orders o on o.id = od.order_id" +
                " where od.id=:id", OrderDish.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderDish> getAll(int orderId) {
        return em.createNativeQuery("select * from order_dishes od" +
                " left join dishes d on d.id = od.dish_id" +
                " left join orders o on o.id = od.order_id" +
                " where o.id=:order_id" +
                " order by od.id", OrderDish.class).
                setParameter("order_id", orderId).
                getResultList();
    }
}
