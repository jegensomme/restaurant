package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaOrderDishRepository;

import java.util.List;

@Repository
public class JpaNativeOrderDishRepository extends JpaOrderDishRepository {

    @Override
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
