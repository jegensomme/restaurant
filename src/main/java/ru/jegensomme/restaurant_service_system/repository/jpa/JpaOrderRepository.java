package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
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
    @SuppressWarnings("unchecked")
    public Order get(int id) {
        List<Order> result = em.createNativeQuery("select * from orders o" +
                " where o.id=:id", Order.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public Order getWithContent(int id) {
        List<Order> result = em.createNativeQuery("select * from orders o" +
                " left join order_dishes od on o.id = od.order_id" +
                " where o.id=:id", Order.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAll() {
        return em.createNativeQuery("select * from orders o" +
                " order by o.date_time", Order.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAllByUser(int userId) {
        return em.createNativeQuery("select * from orders o" +
                " where o.user_id=:user_id" +
                " order by o.date_time", Order.class).
                setParameter("user_id", userId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAllOpened() {
        return em.createNativeQuery("select * from orders o" +
                " where o.status in ('PROCESSING', 'ON_BILL')" +
                " order by o.date_time", Order.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAllOpenedByUser(int userId) {
        return em.createNativeQuery("select * from orders o" +
                " where o.status in ('PROCESSING', 'ON_BILL') and o.user_id=:user_id" +
                " order by o.date_time", Order.class).
                setParameter("user_id", userId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAllByUserShift(int userShiftId) {
        return em.createNativeQuery("select o.* from orders o" +
                " left join user_shifts us on o.user_id = us.user_id and date(o.date_time)=us.date" +
                " where us.id=:user_shift_id" +
                " order by o.date_time", Order.class).
                setParameter("user_shift_id", userShiftId).
                getResultList();
    }
}
