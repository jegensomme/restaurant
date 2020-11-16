package ru.jegensomme.restaurant_service_system.repository;

import java.lang.reflect.Modifier;
import java.util.List;

public interface ModifierRepository {

    Modifier save(Modifier user);

    boolean delete(int id);

    Modifier get(int id);

    List<Modifier> getAll();
}
