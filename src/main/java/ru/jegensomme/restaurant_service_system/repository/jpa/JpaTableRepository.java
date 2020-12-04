package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.repository.TableRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaTableRepository implements TableRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Table save(Table table) {
        if (table.isNew()) {
            em.persist(table);
            return table;
        } else {
            return em.merge(table);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Table.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

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
