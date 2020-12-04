package ru.jegensomme.restaurant_service_system.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.Collections;

import static org.junit.Assert.assertThrows;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.*;

public class DishCategoryServiceTest extends AbstractServiceTest {

    @Before
    public void setUp() {
        clearCache("dish_categories");
    }

    @Autowired
    private DishCategoryService service;

    @Test
    public void create() throws Exception {
        DishCategory created = service.create(getNew());
        int newId = created.id();
        DishCategory newDishCategory = getNew();
        newDishCategory.setId(newId);
        DISH_CATEGORY_MATCHER.assertMatch(created, newDishCategory);
        DISH_CATEGORY_MATCHER.assertMatch(service.get(newId), newDishCategory);
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_CATEGORY1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_CATEGORY1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() throws Exception {
        DishCategory dishCategory = service.get(DISH_CATEGORY1_ID);
        DISH_CATEGORY_MATCHER.assertMatch(dishCategory, DISH_CATEGORY1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void update() throws Exception {
        DishCategory updated = getUpdated();
        service.update(updated);
        DISH_CATEGORY_MATCHER.assertMatch(service.get(DISH_CATEGORY1_ID), updated);
    }

    @Test
    public void getAll() {
        DISH_CATEGORY_MATCHER.assertMatch(service.getAll(),
                DISH_CATEGORY1, DISH_CATEGORY2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllByCategory() {
        DISH_CATEGORY_MATCHER.assertMatch(service.getAllByCategory(DISH_CATEGORY1_ID), Collections.EMPTY_LIST);
    }

    @Test
    public void getAllTop() {
        DISH_CATEGORY_MATCHER.assertMatch(service.getAllTop(),
                DISH_CATEGORY1, DISH_CATEGORY2);
    }
}
