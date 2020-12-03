package ru.jegensomme.restaurant_service_system.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.repository.UserShiftRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaUserShiftRepository implements UserShiftRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public UserShift save(UserShift userShift, int userId) {
        userShift.setUser(em.find(User.class, userId));
        if (userShift.isNew()) {
            if (!getOpenedByUser(userId).isEmpty()) {
                return null;
            }
            em.persist(userShift);
            return userShift;
        } else {
            return em.merge(userShift);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(UserShift.DELETE).
                setParameter("id", id).
                executeUpdate() != 0;
    }

    @Override
    public UserShift get(int id) {
        return null;
    }

    @Override
    public List<UserShift> getAll() {
        return null;
    }

    @Override
    public List<UserShift> getAllByUser(int userId) {
        return null;
    }

    @Override
    public List<UserShift> getOpenedByUser(int userId) {
        return null;
    }

    @Override
    public List<UserShift> getBetweenInclusiveByUser(int userId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Float getTotalSalesByUserShift(int id) {
        return null;
    }
}
