package ru.jegensomme.restaurant_service_system.app.util.to;

import ru.jegensomme.restaurant_service_system.model.DishCategory;

public class DishTo {

    private final Integer id;

    private final String name;

    private final DishCategory category;

    private final Double cost;

    private final boolean isCategory;

    public DishTo(Integer id, String name, DishCategory category, Double cost, boolean isCategory) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.isCategory = isCategory;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DishCategory getCategory() {
        return category;
    }

    public Double getCost() {
        return cost;
    }

    public boolean isCategory() {
        return isCategory;
    }
}
