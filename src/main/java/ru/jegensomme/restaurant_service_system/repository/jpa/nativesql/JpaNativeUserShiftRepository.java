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
    public boolean delete(int id) {
        return super.delete(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserShift get(int id) {
        List<UserShift> result = em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.id=:id", UserShift.class).
                setParameter("id", id).
                getResultList();
        return DataAccessUtils.singleResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserShift> getAll() {
        return em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " order by us.date, us.user_id", UserShift.class).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserShift> getAllByUser(int userId) {
        return em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.user_id=:user_id" +
                " order by us.date", UserShift.class).
                setParameter("user_id", userId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserShift> getOpenedByUser(int userId) {
        return em.createNativeQuery("select * from user_shifts us" +
                " left join users u on u.id = us.user_id" +
                " where us.user_id=:user_id and us.end_time is null" +
                " order by us.date", UserShift.class).
                setParameter("user_id", userId).
                getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserShift> getBetweenInclusiveByUser(int userId, LocalDate startDate, LocalDate endDate) {
        return em.createNativeQuery("""
                select us.* from user_shifts us
                    left join users u on u.id = us.user_id
                where u.id=:user_id and us.date>=:start_date and us.date<=:end_date
                group by us.id""", UserShift.class).setParameter("user_id", userId).
                setParameter("start_date", startDate).
                setParameter("end_date", endDate).
                getResultList();

    }

    @Override
    @SuppressWarnings("unchecked")
    public Float getTotalSalesByUserShift(int id) {
        List<Float> resultList = em.createNativeQuery("""
                select sum(o.check_amount) from user_shifts us
                 left join orders o on us.user_id = o.user_id and us.date=date(o.date_time)
                 where us.id=:id and o.status='CLOSED'""").
                setParameter("id", id).getResultList();
        Float result = DataAccessUtils.singleResult(resultList);
        return result == null ? 0 : result;
    }
}
