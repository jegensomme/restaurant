package ru.jegensomme.restaurant_service_system.repository.jpa;

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
    public Dish get(int id) {
        return null;
    }

    @Override
    public List<Dish> getAll() {
        return null;
    }

    @Override
    public List<Dish> getAllByCategory(int categoryId) {
        return null;
    }

    @Override
    public List<Dish> getAllTop() {
        return null;
    }
}
