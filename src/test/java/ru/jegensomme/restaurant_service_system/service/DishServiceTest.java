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
import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Dish;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.DishTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.DISH_CATEGORY1_ID;

import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = {
        "classpath:db/populateDB.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceTest {

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
    private DishService service;

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
        Dish created = service.create(getNew(), MANAGER_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    public void createNotManager() {
        assertThrows(AccessException.class, () ->
                service.create(getNew(), WAITER1_ID));
    }

    @Test
    public void delete() {
        service.delete(DISH1_ID, MANAGER_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(DISH1_ID);
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
            service.delete(DISH1_ID, WAITER1_ID);
        });
    }

    @Test
    public void get() {
        Dish dish = service.get(DISH1_ID);
        DISH_MATCHER.assertMatch(dish, DISH1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        service.update(updated, MANAGER_ID);
        DISH_MATCHER.assertMatch(service.get(DISH1_ID), updated);
    }

    @Test
    public void updateNotManager() {
        assertThrows(AccessException.class, () -> {
            service.update(getUpdated(), WAITER1_ID);
        });
    }

    @Test
    public void getAll() {
        DISH_MATCHER.assertMatch(service.getAll(),
                DISH1, DISH2, DISH3, DISH4);
    }

    @Test
    public void getAllByCategory() {
        DISH_MATCHER.assertMatch(service.getAllByCategory(DISH_CATEGORY1_ID), DISH1, DISH2);
    }
}
