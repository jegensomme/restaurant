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
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = {
        "classpath:db/populateDB.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class DishCategoryServiceTest {

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
    private DishCategoryService service;

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
        DishCategory created = service.create(getNew(), MANAGER_ID);
        int newId = created.id();
        DishCategory newDishCategory = getNew();
        newDishCategory.setId(newId);
        DISH_CATEGORY_MATCHER.assertMatch(created, newDishCategory);
        DISH_CATEGORY_MATCHER.assertMatch(service.get(newId), newDishCategory);
    }

    @Test
    public void createNotManager() {
        assertThrows(AccessException.class, () ->
                service.create(getNew(), WAITER1_ID));
    }

    @Test
    public void delete() {
        service.delete(DISH_CATEGORY1_ID, MANAGER_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(DISH_CATEGORY1_ID);
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
            service.delete(DISH_CATEGORY1_ID, WAITER1_ID);
        });
    }

    @Test
    public void get() {
        DishCategory dishCategory = service.get(DISH_CATEGORY1_ID);
        DISH_CATEGORY_MATCHER.assertMatch(dishCategory, DISH_CATEGORY1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
    }

    @Test
    public void update() {
        DishCategory updated = getUpdated();
        service.update(updated, MANAGER_ID);
        DISH_CATEGORY_MATCHER.assertMatch(service.get(DISH_CATEGORY1_ID), updated);
    }

    @Test
    public void updateNotManager() {
        assertThrows(AccessException.class, () -> {
            service.update(getUpdated(), WAITER1_ID);
        });
    }

    @Test
    public void getAll() {
        DISH_CATEGORY_MATCHER.assertMatch(service.getAll(),
                DISH_CATEGORY1, DISH_CATEGORY2);
    }

    @Test
    public void getAllByCategory() {
        DISH_CATEGORY_MATCHER.assertMatch(service.getAllByCategory(DISH_CATEGORY1_ID), Collections.EMPTY_LIST);
    }
}
