package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.service.DishService;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Controller
public class DishController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    public Dish create(Dish dish) throws AccessException {
        logger.info("Dish: create {} by {}", dish, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            return service.create(dish);
        } catch (CreateException e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("Dish: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Dish dish) throws AccessException {
        logger.info("Dish: update {} by {}", dish, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.update(dish);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public Dish get(int id) {
        logger.info("Dish: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<Dish> getAll() {
        logger.info("Dish: getAll for {}", SecurityUtil.authUserId());
        return service.getAll();
    }

    public List<Dish> getAllByCategory(int categoryId) {
        logger.info("Dish: getAllByCategory with categoryId={} for {}", categoryId, SecurityUtil.authUserId());
        return service.getAllByCategory(categoryId);
    }

    public List<Dish> getAllTop() {
        logger.info("Dish: getAllTop for {}", SecurityUtil.authUserId());
        return service.getAllTop();
    }
}
