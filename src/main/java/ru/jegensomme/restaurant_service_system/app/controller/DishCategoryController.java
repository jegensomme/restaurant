package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.DishCategory;
import ru.jegensomme.restaurant_service_system.service.DishCategoryService;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;
import java.util.function.Supplier;

@Controller
public class DishCategoryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DishCategoryService service;

    public DishCategoryController(DishCategoryService service) {
        this.service = service;
    }

    public DishCategory create(DishCategory dishCategory) throws AccessException {
        logger.info("DishCategory: create {} by {}", dishCategory, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            return service.create(dishCategory);
        } catch (CreateException e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("DishCategory: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(DishCategory dishCategory) throws AccessException {
        logger.info("DishCategory: update {} by {}", dishCategory, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.update(dishCategory);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public DishCategory get(int id) {
        logger.info("DishCategory: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<DishCategory> getAll() {
        logger.info("DishCategory: getAll for {}", SecurityUtil.authUserId());
        return service.getAll();
    }

    public List<DishCategory> getAllByCategory(int categoryId) {
        logger.info("DishCategory: getAllByCategory with categoryId={} for {}", categoryId, SecurityUtil.authUserId());
        return service.getAllByCategory(categoryId);
    }

    public List<DishCategory> getAllTop() {
        logger.info("DishCategory: getAllTop for {}", SecurityUtil.authUserId());
        return service.getAllTop();
    }

}
