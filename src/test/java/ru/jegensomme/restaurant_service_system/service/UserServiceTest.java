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
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.bridge.SLF4JBridgeHandler;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.testdata.UserTestData;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

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
public class UserServiceTest {

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
    private UserService service;

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
        User created = service.create(UserTestData.getNew());
        int newId = created.id();
        User newUser = UserTestData.getNew();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateKeyCreate() {
        Assert.assertThrows(DataAccessException.class, () -> {
            service.create(new User(null, "Duplicate", UserTestData.WAITER_KEY, Role.WAITER));
        });
    }

    @Test
    public void delete() {
        service.delete(UserTestData.WAITER_ID);
        Assert.assertThrows(NotFoundException.class, () -> {
            service.get(UserTestData.WAITER_ID);
        });
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.delete(UserTestData.NOT_FOUND);
        });
    }

    @Test
    public void get() {
        User user = service.get(UserTestData.WAITER_ID);
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.WAITER);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.get(UserTestData.NOT_FOUND);
        });
    }

    @Test
    public void getByKey() {
        User user = service.getByKey(UserTestData.WAITER_KEY);
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.WAITER);
    }

    @Test
    public void getByKeyNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            service.getByKey(UserTestData.NOT_FOUND_KEY);
        });
    }

    @Test
    public void update() {
        User updated = UserTestData.getUpdated();
        service.update(updated);
        UserTestData.USER_MATCHER.assertMatch(service.get(UserTestData.WAITER_ID), updated);
    }

    @Test
    public void getAll() {
        UserTestData.USER_MATCHER.assertMatch(service.getAll(), UserTestData.MANAGER, UserTestData.WAITER, UserTestData.WAITER2);
    }
}
