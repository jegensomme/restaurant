package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.DishCategory;

import java.util.List;

public interface DishCategoryRepository {

    DishCategory save(DishCategory dishCategory);

    boolean delete(int id);

    DishCategory get(int id);

    List<DishCategory> getAll();

    List<DishCategory> getAllByCategory(int categoryId);
}
