package ru.jegensomme.restaurant_service_system.app.util.to.util;

import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.app.util.to.OrderTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderUtil {

    private OrderUtil() {
    }

    public static List<OrderTo> getTos(Collection<Order> orders) {
        return filterByPredicate(orders, order -> true);
    }

    public static List<OrderTo> filterByPredicate(Collection<Order> orders, Predicate<Order> filter) {
        return orders.stream()
                .filter(filter)
                .map(OrderUtil::createTo)
                .collect(Collectors.toList());
    }

    private static OrderTo createTo(Order order) {
        return new OrderTo(order.id(), order.getUser(), order.getDateTime(),
                order.getTable(), order.getStatus(), order.getDiscount());
    }

    public static double getCostWithDiscount(double cost, int discount) {
        if (!(discount >= 0 && discount <= 100)) {
            throw new IllegalArgumentException();
        }
        return cost * (1 - discount/100.0);
    }
}
