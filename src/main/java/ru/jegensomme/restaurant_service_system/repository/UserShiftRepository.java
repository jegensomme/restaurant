package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.app.util.to.UserShiftTo;

import java.time.LocalDate;
import java.util.List;

public interface UserShiftRepository {

    UserShift save(UserShift userShift, int userId);

    boolean delete(int id);

    UserShift get(int id);

    List<UserShift> getAll();

    List<UserShift> getAllByUser(int userId);

    List<UserShift> getOpenedByUser(int userId);

    List<UserShift> getBetweenInclusive(LocalDate startDate, LocalDate endDate);

    List<UserShift> getBetweenInclusiveByUser(int userId, LocalDate startDate, LocalDate endDate);

    Float getTotalSalesByUserShift(int id);
}
