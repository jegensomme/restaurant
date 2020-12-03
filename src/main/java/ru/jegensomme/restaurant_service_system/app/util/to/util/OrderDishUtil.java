package ru.jegensomme.restaurant_service_system.app.util.to.util;

import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.app.util.to.OrderDishTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderDishUtil {

    private OrderDishUtil() {
    }

    public static List<OrderDishTo> getTos(Collection<OrderDish> orderDishes) {
        return filterByPredicate(orderDishes, orderDish -> true);
    }

    public static List<OrderDishTo> filterByPredicate(Collection<OrderDish> orderDishes, Predicate<OrderDish> filter) {
        return orderDishes.stream()
                .filter(filter)
                .map(orderDish -> {
                    int amount = orderDish.getAmount();
                    double cost = orderDish.getDish().getCost();
                    return createTo(orderDish, amount*cost);
                })
                .collect(Collectors.toList());
    }

    private static OrderDishTo createTo(OrderDish orderDish, double cost) {
        return new OrderDishTo(orderDish.getId(), orderDish.getDish(),
                orderDish.getAmount(), orderDish.getComment(), cost);
    }
}
