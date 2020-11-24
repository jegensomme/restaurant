package ru.jegensomme.restaurant_service_system.repository.jdbc;

import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.repository.DishCategoryRepository;

import java.util.List;

public class JdbcDishCategoryRepository implements DishCategoryRepository {

    @Override
    public DishCategory save(DishCategory dishCategory) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public DishCategory get(int id) {
        return null;
    }

    @Override
    public List<DishCategory> getAll() {
        return null;
    }

    @Override
    public List<DishCategory> getAllByCategory(int categoryId) {
        return null;
    }
}
