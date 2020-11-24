package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.repository.DishModifierRepository;

import java.util.List;

@Repository
public class JdbcDishModifierRepository implements DishModifierRepository {

    @Override
    public DishModifier save(DishModifier dishModifier) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public DishModifier get(int id) {
        return null;
    }

    @Override
    public List<DishModifier> getAll() {
        return null;
    }
}
