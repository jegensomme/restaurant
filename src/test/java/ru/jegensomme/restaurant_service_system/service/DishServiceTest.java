package ru.jegensomme.restaurant_service_system.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
/*import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Dish;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.MANAGER_ID;
import static ru.jegensomme.restaurant_service_system.testdata.UserTestData.WAITER1_ID;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.DISH_CATEGORY1_ID;

import ru.jegensomme.restaurant_service_system.model.DishModifier;
import ru.jegensomme.restaurant_service_system.util.JpaUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;
import static org.junit.Assert.assertThrows;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() {
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelHibernateCache();
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
        assertThrows(NotFoundException.class, () -> {
            service.get(DISH1_ID);
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
        Dish dish = service.get(DISH1_ID);
        DISH_MATCHER.assertMatch(dish, DISH1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> {
            service.get(NOT_FOUND);
        });
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
    public void getAllTop() {
        DISH_MATCHER.assertMatch(service.getAllTop(), Collections.EMPTY_LIST);
    }
}*/
