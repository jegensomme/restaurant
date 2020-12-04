package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User get(int id) {
        List<User> result = em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where u.id=:id", User.class).
                setParameter("id", id).
                getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getByKey(String key) {
        List<User> result = em.createNativeQuery("select u.*, role from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where key=:key", User.class).
                setParameter("key", key).
                getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " order by ur.role, u.name", User.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllByRole(Role role) {
        return em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where ur.role=:role" +
                " order by u.name", User.class).
                setParameter("role", role.toString()).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllByShiftDate(LocalDate date) {
        return em.createNativeQuery("select u.*, ur.role from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " left join user_shifts us on u.id = us.user_id" +
                " where us.date=:date" +
                " order by u.name", User.class).
                setParameter("date", date).
                getResultList();
    }
}
