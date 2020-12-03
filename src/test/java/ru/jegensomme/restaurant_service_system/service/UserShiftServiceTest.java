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
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.UserShiftTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER2_ID;

public class UserShiftServiceTest extends AbstractServiceTest {
/*
    @Autowired
    private UserShiftService service;

    @Test
    public void create() throws Exception {
        UserShift created = service.create(getNew(), WAITER1_ID);
        int newId = created.id();
        UserShift newUserShift = getNew();
        newUserShift.setId(newId);
        USER_SHIFT_MATCHER.assertMatch(created, newUserShift);
        USER_SHIFT_MATCHER.assertMatch(service.get(newId), newUserShift);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_SHIFT1_ID);
        assertThrows(NotFoundException.class, () -> {
            service.get(USER_SHIFT1_ID);
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
        UserShift userShift = service.get(USER_SHIFT1_ID);
        USER_SHIFT_MATCHER.assertMatch(userShift, USER_SHIFT1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
    }

    @Test
    public void update() throws Exception {
        UserShift updated = getUpdated();
        service.update(updated, WAITER1_ID);
        USER_SHIFT_MATCHER.assertMatch(service.get(USER_SHIFT4_ID), updated);
    }

    @Test
    public void getAll() {
        USER_SHIFT_MATCHER.assertMatch(service.getAll(),
                USER_SHIFT1, USER_SHIFT2, USER_SHIFT3, USER_SHIFT4, USER_SHIFT5, USER_SHIFT6);
    }

    @Test
    public void getAllByUser() {
        USER_SHIFT_MATCHER.assertMatch(service.getAllByUser(WAITER1_ID),
                USER_SHIFT1, USER_SHIFT4);
    }*/
}
