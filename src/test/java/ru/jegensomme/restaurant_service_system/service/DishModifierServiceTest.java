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
import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.DishModifierTestData.*;
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
public class DishModifierServiceTest {

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
    private DishModifierService service;

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
        DishModifier created = service.create(getNew(), MANAGER_ID);
        int newId = created.id();
        DishModifier newDishModifier = getNew();
        newDishModifier.setId(newId);
        DISH_MODIFIER_MATCHER.assertMatch(created, newDishModifier);
        DISH_MODIFIER_MATCHER.assertMatch(service.get(newId), newDishModifier);
    }

    @Test
    public void createNotManager() {
        assertThrows(AccessException.class, () ->
                service.create(getNew(), WAITER1_ID));
    }

    @Test
    public void delete() {
        service.delete(DISH_MODIFIER1_ID, MANAGER_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(DISH_MODIFIER1_ID);
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
            service.delete(DISH_MODIFIER1_ID, WAITER1_ID);
        });
    }

    @Test
    public void get() {
        DishModifier dishModifier = service.get(DISH_MODIFIER1_ID);
        DISH_MODIFIER_MATCHER.assertMatch(dishModifier, DISH_MODIFIER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
    }

    @Test
    public void update() {
        DishModifier updated = getUpdated();
        service.update(updated, MANAGER_ID);
        DISH_MODIFIER_MATCHER.assertMatch(service.get(DISH_MODIFIER1_ID), updated);
    }

    @Test
    public void updateNotManager() {
        assertThrows(AccessException.class, () -> {
            service.update(getUpdated(), WAITER1_ID);
        });
    }

    @Test
    public void getAll() {
        DISH_MODIFIER_MATCHER.assertMatch(service.getAll(), DISH_MODIFIER1, DISH_MODIFIER2);
    }
}
