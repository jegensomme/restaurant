package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public User get(int id) {
        return null;
    }

    @Override
    public User getByKey(String key) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getAllByRole(Role role) {
        return null;
    }

}
