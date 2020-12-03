package ru.jegensomme.restaurant_service_system.testdata;
/*
import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;
import static ru.jegensomme.restaurant_service_system.testdata.TableTestData.TABLE1;
import static ru.jegensomme.restaurant_service_system.testdata.TableTestData.TABLE2;
import static ru.jegensomme.restaurant_service_system.testdata.TableTestData.TABLE3;
import static ru.jegensomme.restaurant_service_system.testdata.TableTestData.TABLE4;


public class OrderTestData {

    public static final TestMatcher<Order> ORDER_MATCHER = TestMatcher.usingFieldsComparator("user", "dishes");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int ORDER1_ID = START_SEQ + 13;
    public static final int ORDER2_ID = ORDER1_ID + 1;

    public static final int ORDER3_ID = ORDER1_ID + 2;
    public static final int ORDER4_ID = ORDER1_ID + 3;

    public static Order ORDER1 = new Order(ORDER1_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 10, 10, 0),
            TABLE1, OrderStatus.PROCESSING, 0);

    public static Order ORDER2 = new Order(ORDER2_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 10, 12, 0),
            TABLE2, OrderStatus.CLOSED, 0);

    public static Order ORDER3 = new Order(ORDER3_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 11, 10, 0),
            TABLE3, OrderStatus.CLOSED, 0);
    public static Order ORDER4 = new Order(ORDER4_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 11, 12, 0),
            TABLE4, OrderStatus.PROCESSING, 0);

    public static Order getNew() {
        return new Order(null,
                LocalDateTime.of(2020, Month.OCTOBER, 12, 10, 0),
                TABLE1, OrderStatus.PROCESSING, 0);
    }

    public static Order getUpdated() {
        Order updated = new Order(ORDER1);
        updated.setStatus(OrderStatus.ON_BILL);
        return updated;
    }

}
*/