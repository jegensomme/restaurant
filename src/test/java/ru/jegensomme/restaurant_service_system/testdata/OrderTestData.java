package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.to.DishTo;
import ru.jegensomme.restaurant_service_system.testdata.DishTestData;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


public class OrderTestData {

    public static final TestMatcher<Order> ORDER_MATCHER = TestMatcher.usingFieldsComparator();

    public static final int NOT_FOUND = 10;
    public static final int NEW = 3;

    public static final int ORDER1_ID = 1;
    public static final int ORDER2_ID = 2;

    public static final List<OrderDish> CONTENT1 = Arrays.asList(
            new OrderDish(new Order(ORDER1_ID), DishTestData.DISH11, 1, "comment"),
            new OrderDish(new Order(ORDER1_ID), DishTestData.DISH12, 1, "comment")
    );

    public static final List<OrderDish> CONTENT2 = Arrays.asList(
            new OrderDish(new Order(ORDER2_ID), DishTestData.DISH21, 1, "comment"),
            new OrderDish(new Order(ORDER2_ID), DishTestData.DISH22, 1, "comment")
    );

    public static final List<OrderDish> NEW_CONTENT = Arrays.asList(
            new OrderDish(new Order(NEW), new Dish(DishTestData.DISH11), 1, "comment"),
            new OrderDish(new Order(NEW), new Dish(DishTestData.DISH22), 1, "comment")
    );

    public static Order ORDER1 = new Order(ORDER1_ID, LocalDateTime.of(2020, Month.OCTOBER, 10, 10, 0),
            1, OrderStatus.PROCESSING, 0, CONTENT1);

    public static Order ORDER2 = new Order(ORDER2_ID, LocalDateTime.of(2020, Month.OCTOBER, 12, 12, 0),
            2, OrderStatus.PROCESSING, 0, CONTENT2);

    public static Order getNew() {
        return new Order(null, LocalDateTime.of(2020, Month.OCTOBER, 14, 10, 0), 1, OrderStatus.PROCESSING, 0, NEW_CONTENT);
    }

    public static Order getUpdated() {
        Order updated = new Order(ORDER1);
        updated.setStatus(OrderStatus.ON_BILL);
        return updated;
    }

}
