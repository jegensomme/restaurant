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
import ru.jegensomme.restaurant_service_system.model.OrderDish;
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

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = {
        "classpath:db/populateDB.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class OrderDishServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    private static final Logger log = getLogger("result");

    private static final StringBuilder results = new StringBuilder();

    @Rule
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @Autowired
    private OrderDishService service;

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }

    @Test
    public void create() {
        OrderDish created = service.create(getNew(), MANAGER_ID);
        int newId = created.id();
        OrderDish newDish = getNew();
        newDish.setId(newId);
        ORDER_DISH_MATCHER.assertMatch(created, newDish);
        ORDER_DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    public void createNotOwn() {
        assertThrows(AccessException.class, () ->
                service.create(getNew(), WAITER2_ID));
    }

    @Test
    public void delete() {
        service.delete(ORDER_DISH_ID1, MANAGER_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(ORDER_DISH_ID1);
        });
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.delete(NOT_FOUND, MANAGER_ID);
        });
    }

    @Test
    public void deleteNotManager() {
        assertThrows(AccessException.class, () -> {
            service.delete(ORDER_DISH_ID1, WAITER1_ID);
        });
    }

    @Test
    public void get() {
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
    public void update() {
        OrderDish updated = getUpdated();
        service.update(updated, MANAGER_ID);
        ORDER_DISH_MATCHER.assertMatch(service.get(ORDER_DISH_ID1), updated);
    }

    @Test
    public void updateNotManager() {
        assertThrows(AccessException.class, () -> {
            service.update(getUpdated(), WAITER1_ID);
        });
    }

    @Test
    public void getAll() {
        ORDER_DISH_MATCHER.assertMatch(service.getAll(ORDER1_ID), ORDER_DISH1, ORDER_DISH2);
    }
}
