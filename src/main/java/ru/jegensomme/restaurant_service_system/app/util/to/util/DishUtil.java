package ru.jegensomme.restaurant_service_system.app.util.to.util;

import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.app.util.to.DishTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DishUtil {

    private DishUtil() {
    }

    public static <T extends DishCategory> List<DishTo> getTos(Collection<T> dishes) {
        return filterByPredicate(dishes, dish -> true);
    }

    public static <T extends DishCategory> List<DishTo> filterByPredicate(Collection<T> dishes, Predicate<T> filter) {
        return dishes.stream()
                .filter(filter)
                .map(dish -> createTo(dish, dish.getClass() == DishCategory.class))
                .collect(Collectors.toList());
    }

    private static <T extends DishCategory> DishTo createTo(T dish, boolean isCategory) {
        return new DishTo(dish.getId(), dish.getName(), dish.getCategory(),
                isCategory ? null : ((Dish)dish).getCost(), isCategory);
    }
}
