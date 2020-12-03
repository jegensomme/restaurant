package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.repository.DishCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaDishCategoryRepository implements DishCategoryRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public DishCategory save(DishCategory dishCategory) {
        if (dishCategory.isNew()) {
            em.persist(dishCategory);
            return dishCategory;
        } else {
            return em.merge(dishCategory);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(DishCategory.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    public DishCategory get(int id) {
        return null;
    }

    @Override
    public List<DishCategory> getAll() {
        return null;
    }

    @Override
    public List<DishCategory> getAllByCategory(int categoryId) {
        return null;
    }

    @Override
    public List<DishCategory> getAllTop() {
        return null;
    }
}
