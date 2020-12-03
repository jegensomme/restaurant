package ru.jegensomme.restaurant_service_system.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jegensomme.restaurant_service_system.model.Dish;
/*import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.OrderDishTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.OrderTestData.ORDER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER2_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;

public class OrderDishServiceTest extends AbstractServiceTest {

    @Autowired
    private OrderDishService service;

    @Test
    public void create() throws Exception {
        OrderDish created = service.create(getNew());
        int newId = created.id();
        OrderDish newDish = getNew();
        newDish.setId(newId);
        ORDER_DISH_MATCHER.assertMatch(created, newDish);
        ORDER_DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ORDER_DISH_ID1);
        assertThrows(NotFoundException.class, () -> {
            service.get(ORDER_DISH_ID1);
        });
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.delete(NOT_FOUND);
        });
    }

    @Test
    public void get() throws Exception {
        OrderDish dish = service.get(ORDER_DISH_ID1);
        ORDER_DISH_MATCHER.assertMatch(dish, ORDER_DISH1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
    }

    @Test
    public void update() throws Exception {
        OrderDish updated = getUpdated();
        service.update(updated);
        ORDER_DISH_MATCHER.assertMatch(service.get(ORDER_DISH_ID1), updated);
    }

    @Test
    public void getAll() {
        ORDER_DISH_MATCHER.assertMatch(service.getAll(ORDER1_ID), ORDER_DISH1, ORDER_DISH2);
    }
}*/
