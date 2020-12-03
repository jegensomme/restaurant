package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaTableRepository;

import java.util.List;

@Repository
public class JpaNativeTableRepository extends JpaTableRepository {

    @Override
    @SuppressWarnings("unchecked")
    public Table get(int id) {
        List<Table> result = em.createNativeQuery("select * from tables t" +
                " where t.id=:id", Table.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Table> getAll() {
        return em.createNativeQuery("select * from tables t" +
                " order by t.number", Table.class).
                getResultList();
    }
}
