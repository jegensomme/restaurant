package ru.jegensomme.restaurant_service_system.repository.jpa.nativesql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.repository.jpa.JpaUserShiftRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaNativeUserShiftRepository extends JpaUserShiftRepository {

    @Override
    public UserShift get(int id) {
        List<UserShift> result = em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.id=:id", UserShift.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public UserShift getByUserDate(int userId, LocalDate date) {
        List<UserShift> result = em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.user_id=:user_id and date(us.start_date_time)=:date", UserShift.class).
                setParameter("user_id", userId).
                setParameter("date", date).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<UserShift> getAll() {
        return em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " order by date(us.start_date_time), us.user_id", UserShift.class).
                getResultList();
    }

    @Override
    public List<UserShift> getAllByUser(int userId) {
        return em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.user_id=:user_id" +
                " order by date(us.start_date_time)", UserShift.class).
                setParameter("user_id", userId).
                getResultList();
    }
}
