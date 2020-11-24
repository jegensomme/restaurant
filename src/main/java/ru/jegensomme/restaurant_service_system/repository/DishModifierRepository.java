package ru.jegensomme.restaurant_service_system.repository;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.DishModifier;

import java.util.List;

@Repository
public interface DishModifierRepository {

    DishModifier save(DishModifier dishModifier);

    boolean delete(int id);

    DishModifier get(int id);

    List<DishModifier> getAll();
}
