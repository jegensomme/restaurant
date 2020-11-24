package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.repository.DishModifierRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaDishModifierRepository implements DishModifierRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public DishModifier save(DishModifier dishModifier) {
        if (dishModifier.isNew()) {
            em.persist(dishModifier);
            return dishModifier;
        } else {
            return em.merge(dishModifier);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(DishModifier.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    public DishModifier get(int id) {
        return null;
    }

    @Override
    public List<DishModifier> getAll() {
        return null;
    }
}
