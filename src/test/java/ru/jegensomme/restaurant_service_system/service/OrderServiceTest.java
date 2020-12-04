package ru.jegensomme.restaurant_service_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.testdata.OrderDishTestData;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;

import static ru.jegensomme.restaurant_service_system.testdata.OrderTestData.*;
import static ru.jegensomme.restaurant_service_system.testdata.UserShiftTestData.USER_SHIFT1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;

public class OrderServiceTest extends AbstractServiceTest {

    @Autowired
    private OrderService service;

    @Test
    public void create() throws Exception {
        Order newOrder = getNew();
        Order created = service.create(newOrder, WAITER1_ID);
        int newId = created.id();
        newOrder.setId(newId);
        ORDER_MATCHER.assertMatch(created, newOrder);
        ORDER_MATCHER.assertMatch(service.get(newId), newOrder);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ORDER1_ID);
        assertThrows(NotFoundException.class, () ->
            service.get(ORDER1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.delete(NOT_FOUND));
    }

    @Test
    public void get() throws Exception {
        Order order = service.get(ORDER1_ID);
        ORDER_MATCHER.assertMatch(order, ORDER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
            service.get(NOT_FOUND));
    }

    @Test
    public void update() throws Exception {
        Order updated = getUpdated();
        service.update(updated, WAITER1_ID);
        ORDER_MATCHER.assertMatch(service.get(ORDER1_ID), updated);
    }

    @Test
    public void getAll() {
        ORDER_MATCHER.assertMatch(service.getAll(), ORDER1, ORDER2, ORDER3, ORDER4);
    }

    @Test
    public void getAllByUser() {
        ORDER_MATCHER.assertMatch(service.getAllByUser(WAITER1_ID), ORDER1, ORDER3);
    }

    @Test
    public void getAllOpened() {
        ORDER_MATCHER.assertMatch(service.getAllOpened(), ORDER1, ORDER4);
    }

    @Test
    public void getAllOpenedByUser() {
        ORDER_MATCHER.assertMatch(service.getAllOpenedByUser(WAITER1_ID), ORDER1);
    }

    @Test
    public void getAllByUserShift() {
        ORDER_MATCHER.assertMatch(service.getAllByUserShift(USER_SHIFT1_ID), ORDER1);
    }
}
