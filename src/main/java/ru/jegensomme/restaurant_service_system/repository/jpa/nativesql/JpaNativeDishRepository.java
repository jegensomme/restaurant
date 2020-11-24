package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaDishRepository;

import java.util.List;

@Repository
public class JpaNativeDishRepository extends JpaDishRepository {

    @Override
    public Dish get(int id) {
        List<Dish> result = em.createNativeQuery("select * from dishes d" +
                " left join dishes_modifiers dm on d.id = dm.dish_id" +
                " where d.id=:id", Dish.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<Dish> getAll() {
        return em.createNativeQuery("select * from dishes d" +
                " left join dishes_modifiers dm on d.id = dm.dish_id" +
                " order by d.category_id, d.name", Dish.class).
                getResultList();
    }

    @Override
    public List<Dish> getAllByCategory(int categoryId) {
        return em.createNativeQuery("select * from dishes d" +
                " where d.category_id=:category_id" +
                " order by d.name", Dish.class).
                setParameter("category_id", categoryId).
                getResultList();
    }
}
