package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.repository.DishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaDishRepository implements DishRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Dish save(Dish dish) {
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Dish.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dish get(int id) {
        List<Dish> result = em.createNativeQuery("select * from dishes d" +
                " where d.id=:id", Dish.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dish> getAll() {
        return em.createNativeQuery("select * from dishes d" +
                " order by d.category_id, d.name", Dish.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dish> getAllByCategory(int categoryId) {
        return em.createNativeQuery("select * from dishes d" +
                " where d.category_id=:category_id" +
                " order by d.category_id, d.name", Dish.class).
                setParameter("category_id", categoryId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dish> getAllTop() {
        return em.createNativeQuery("select * from dishes d" +
                " where d.category_id is null" +
                " order by d.name", Dish.class).
                getResultList();
    }
}
