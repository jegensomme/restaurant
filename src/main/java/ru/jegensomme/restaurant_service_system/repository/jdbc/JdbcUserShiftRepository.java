package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.repository.UserShiftRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcUserShiftRepository implements UserShiftRepository {

    @Override
    public UserShift save(UserShift userShift, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public UserShift get(int id) {
        return null;
    }

    @Override
    public UserShift getByUserDate(int userId, LocalDate date) {
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
}
