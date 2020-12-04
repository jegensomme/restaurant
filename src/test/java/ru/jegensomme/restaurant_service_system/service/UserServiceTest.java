package ru.jegensomme.restaurant_service_system.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;

import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.UserShiftTestData.USER_SHIFT1;

public class UserServiceTest extends AbstractServiceTest {

    @Before
    public void setUp() {
        clearCache("users");
    }

    @Autowired
    private UserService service;

    @Test
    public void create() throws Exception {
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
    public void delete() throws Exception {
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
    public void get() throws Exception {
        User user = service.get(WAITER1_ID);
        USER_MATCHER.assertMatch(user, WAITER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.get(NOT_FOUND));
    }

    @Test
    public void getByKey() throws Exception {
        User user = service.getByKey(WAITER_KEY);
        USER_MATCHER.assertMatch(user, WAITER1);
    }

    @Test
    public void getByKeyNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.getByKey(NOT_FOUND_KEY));
    }

    @Test
    public void update() throws Exception {
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

    @Test
    public void getAllByShiftDate() {
        USER_MATCHER.assertMatch(service.getAllByShiftDate(USER_SHIFT1.getDate()),
                MANAGER, WAITER1, WAITER2);
    }
}
