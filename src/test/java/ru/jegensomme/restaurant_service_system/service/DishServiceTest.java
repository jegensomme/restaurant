package ru.jegensomme.restaurant_service_system.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jegensomme.restaurant_service_system.model.Dish;

import java.util.Collections;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.DISH_CATEGORY1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.DishTestData.*;

import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;
import static org.junit.Assert.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Before
    public void setUp() {
        clearCache("dishes");
    }

    @Autowired
    private DishService service;

    @Test
    public void create() throws Exception {
        Dish created = service.create(getNew());
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() throws Exception {
        Dish dish = service.get(DISH1_ID);
        DISH_MATCHER.assertMatch(dish, DISH1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void update() throws Exception {
        Dish updated = getUpdated();
        service.update(updated);
        DISH_MATCHER.assertMatch(service.get(DISH1_ID), updated);
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

    @Test
    @SuppressWarnings("unchecked")
    public void getAllTop() {
        DISH_MATCHER.assertMatch(service.getAllTop(), Collections.EMPTY_LIST);
    }
}
