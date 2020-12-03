package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.service.OrderDishService;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Controller
public class OrderDishController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrderDishService service;

    public OrderDishController(OrderDishService service) {
        this.service = service;
    }

    public OrderDish create(OrderDish orderDish, int userId) throws AccessException {
        logger.info("OrderDish: create {} by {}", orderDish, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        try {
            return service.create(orderDish);
        } catch (CreateException e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("OrderDish: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(OrderDish orderDish, int userId) throws AccessException {
        logger.info("OrderDish: update {} by {}", orderDish, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        try {
            service.update(orderDish);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public OrderDish get(int id) {
        logger.info("OrderDish: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<OrderDish> getAll(int orderId) {
        logger.info("OrderDish: getAll with orderId={} for {}", orderId, SecurityUtil.authUserId());
        return service.getAll(orderId);
    }
}
