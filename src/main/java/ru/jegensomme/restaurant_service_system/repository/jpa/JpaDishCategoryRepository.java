package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
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
    @SuppressWarnings("unchecked")
    public DishCategory get(int id) {
        List<DishCategory> result = em.createNativeQuery("select *, 0 clazz_ from dish_categories d" +
                " where d.id=:id", DishCategory.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DishCategory> getAll() {
        return em.createNativeQuery("select *, 0 clazz_ from dish_categories d" +
                " order by d.name", DishCategory.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DishCategory> getAllByCategory(int categoryId) {
        return em.createNativeQuery("select *, 0 clazz_ from dish_categories d" +
                " where d.category_id=:category_id" +
                " order by d.category_id, d.name", DishCategory.class).
                setParameter("category_id", categoryId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DishCategory> getAllTop() {
        return em.createNativeQuery("select *, 0 clazz_ from dish_categories d" +
                " where d.category_id is null" +
                " order by d.name", DishCategory.class).
                getResultList();
    }
}
