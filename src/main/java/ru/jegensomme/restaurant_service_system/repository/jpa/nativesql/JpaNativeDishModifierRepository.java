package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaDishModifierRepository;

import java.util.List;

@Repository
public class JpaNativeDishModifierRepository extends JpaDishModifierRepository {

    @Override
    public DishModifier get(int id) {
        List<DishModifier> result = em.createNativeQuery("select * from modifiers m" +
                " where m.id=:id", DishModifier.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<DishModifier> getAll() {
        return em.createNativeQuery("select * from modifiers d" +
                " order by d.name", DishModifier.class).
                getResultList();
    }
}
