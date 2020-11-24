package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.repository.DishRepository;

import java.util.List;

@Repository
public class JdbcDishRepository implements DishRepository {

    @Override
    public Dish save(Dish user) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Dish get(int id) {
        return null;
    }

    @Override
    public List<Dish> getAll() {
        return null;
    }

    @Override
    public List<Dish> getAllByCategory(int categoryId) {
        return null;
    }
}
