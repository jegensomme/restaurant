package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaUserRepository;
import java.util.List;

@Repository
public class JpaNativeUserRepository extends JpaUserRepository {

    @Override
    public User get(int id) {
        List<User> result = em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where u.id=:id", User.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public User getByKey(String key) {
        List<User> result = em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where key=:key", User.class).
                setParameter("key", key).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<User> getAll() {
        return em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " order by ur.role, u.name asc", User.class).
                getResultList();
    }

    @Override
    public List<User> getAllByRole(Role role) {
        return em.createNativeQuery("select * from users u" +
                " left join user_roles ur on u.id = ur.user_id" +
                " where ur.role=:role" +
                " order by u.name asc", User.class).
                setParameter("role", role.toString()).
                getResultList();
    }
}
