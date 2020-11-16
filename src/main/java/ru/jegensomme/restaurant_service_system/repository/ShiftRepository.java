package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Shift;

import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository {

    Shift save(Shift user);

    boolean delete(int id);

    Shift get(int id);

    List<Shift> getAll();
}
