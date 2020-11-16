package ru.jegensomme.restaurant_service_system.service;

import org.junit.AfterClass;
import org.junit.Assert;
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
import ru.jegensomme.restaurant_service_system.testdata.OrderTestData;
import ru.jegensomme.restaurant_service_system.testdata.UserTestData;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Order created = service.create(OrderTestData.getNew(), UserTestData.WAITER_ID);
        Order newOrder = OrderTestData.getNew();
        int newId = created.id();
        newOrder.setId(newId);
        OrderTestData.ORDER_MATCHER.assertMatch(created, newOrder);
        OrderTestData.ORDER_MATCHER.assertMatch(service.get(newId), newOrder);
    }

    @Test
    public void delete() {
        service.delete(OrderTestData.ORDER1_ID, UserTestData.MANAGER_ID);
        Assert.assertThrows(NotFoundException.class, () -> {
            service.get(OrderTestData.ORDER1_ID);
        });
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.delete(OrderTestData.NOT_FOUND, UserTestData.MANAGER_ID);
        });
    }

    @Test
    public void deleteNotManager() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.delete(OrderTestData.ORDER1_ID, UserTestData.WAITER_ID);
        });
    }

    @Test
    public void get() {
        Order order = service.get(OrderTestData.ORDER1_ID);
        OrderTestData.ORDER_MATCHER.assertMatch(order, OrderTestData.ORDER1);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.get(OrderTestData.NOT_FOUND);
        });
    }

    @Test
    public void update() {
        Order updated = OrderTestData.getUpdated();
        service.update(updated, UserTestData.WAITER_ID);
        OrderTestData.ORDER_MATCHER.assertMatch(service.get(OrderTestData.ORDER1_ID), updated);
    }

    @Test
    public void updateNotOwn() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.update(OrderTestData.getUpdated(), UserTestData.MANAGER_ID);
        });
    }

    @Test
    public void getAll() {
        List<Order> orders = service.getAll();
        OrderTestData.ORDER_MATCHER.assertMatch(service.getAll(),
                OrderTestData.ORDER1, OrderTestData.ORDER2);
    }

    @Test
    public void getAllByWaiter() {
        OrderTestData.ORDER_MATCHER.assertMatch(service.getAllByWaiter(UserTestData.WAITER_ID),
                OrderTestData.ORDER1);
    }
}
