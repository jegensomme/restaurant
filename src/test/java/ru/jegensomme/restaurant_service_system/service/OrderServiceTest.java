package ru.jegensomme.restaurant_service_system.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.bridge.SLF4JBridgeHandler;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;

import static org.junit.Assert.assertThrows;
import java.util.concurrent.TimeUnit;

import static ru.jegensomme.restaurant_service_system.testdata.OrderTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER2_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = {
        "classpath:db/populateDB.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class OrderServiceTest {

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
    private OrderService service;

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
        Order created = service.create(getNew(), WAITER1_ID);
        Order newOrder = getNew();
        int newId = created.id();
        newOrder.setId(newId);
        ORDER_MATCHER.assertMatch(created, newOrder);
        ORDER_MATCHER.assertMatch(service.get(newId), newOrder);
    }

    @Test
    public void delete() {
        service.delete(ORDER1_ID, MANAGER_ID);
        assertThrows(NotFoundException.class, () ->
            service.get(ORDER1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.delete(NOT_FOUND, MANAGER_ID));
    }

    @Test
    public void get() {
        Order order = service.get(ORDER1_ID);
        ORDER_MATCHER.assertMatch(order, ORDER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.get(NOT_FOUND));
    }

    @Test
    public void update() {
        Order updated = getUpdated();
        service.update(updated, WAITER1_ID);
        ORDER_MATCHER.assertMatch(service.get(ORDER1_ID), updated);
    }

    @Test
    public void updateNotOwn() {
        assertThrows(AccessException.class, () ->
            service.update(getUpdated(), WAITER2_ID));
    }

    @Test
    public void getAll() {
        ORDER_MATCHER.assertMatch(service.getAll(), ORDER1, ORDER2, ORDER3, ORDER4);
    }

    @Test
    public void getAllByWaiter() {
        ORDER_MATCHER.assertMatch(service.getAllByUser(WAITER1_ID), ORDER1, ORDER3);
    }
}
