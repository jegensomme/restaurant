package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaOrderRepository;

import java.util.List;

@Repository
public class JpaNativeOrderRepository extends JpaOrderRepository {

    @Override
    public Order get(int id) {
        List<Order> result = em.createNativeQuery("select * from orders o" +
                " where o.id=:id", Order.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<Order> getAll() {
        return em.createNativeQuery("select * from orders o" +
                " order by o.date_time", Order.class).
                getResultList();
    }

    @Override
    public List<Order> getAllByUser(int userId) {
        return em.createNativeQuery("select * from orders o" +
                " where o.user_id=:user_id" +
                " order by o.date_time", Order.class).
                setParameter("user_id", userId).
                getResultList();
    }
}
