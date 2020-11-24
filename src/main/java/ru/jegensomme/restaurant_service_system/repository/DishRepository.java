package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Dish;

import java.util.List;

public interface DishRepository {

    Dish save(Dish user);

    boolean delete(int id);

    Dish get(int id);

    List<Dish> getAll();

    List<Dish> getAllByCategory(int categoryId);
}
