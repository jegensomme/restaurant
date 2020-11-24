package ru.jegensomme.restaurant_service_system.service;

import org.junit.AfterClass;
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
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;

import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.*;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@ContextConfiguration({
        "classpath*:spring/spring-app.xml",
        "classpath*:spring/spring-db.xml"
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
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateKeyCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", WAITER_KEY, Role.WAITER)));
    }

    @Test
    public void delete() {
        service.delete(WAITER1_ID);
        assertThrows(NotFoundException.class, () ->
            service.get(WAITER1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        User user = service.get(WAITER1_ID);
        USER_MATCHER.assertMatch(user, WAITER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.get(NOT_FOUND));
    }

    @Test
    public void getByKey() {
        User user = service.getByKey(WAITER_KEY);
        USER_MATCHER.assertMatch(user, WAITER1);
    }

    @Test
    public void getByKeyNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.getByKey(NOT_FOUND_KEY));
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(WAITER1_ID), updated);
    }

    @Test
    public void getAll() {
        USER_MATCHER.assertMatch(service.getAll(), MANAGER, WAITER1, WAITER2);
    }

    @Test
    public void getAllByRole() {
        USER_MATCHER.assertMatch(service.getAllByRole(Role.WAITER), WAITER1, WAITER2);
    }
}
