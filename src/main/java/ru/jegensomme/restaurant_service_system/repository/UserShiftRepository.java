package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.UserShift;

import java.time.LocalDate;
import java.util.List;

public interface UserShiftRepository {

    UserShift save(UserShift userShift, int userId);

    boolean delete(int id);

    UserShift get(int id);

    UserShift getByUserDate(int userId, LocalDate date);

    List<UserShift> getAll();

    List<UserShift> getAllByUser(int userId);
}
