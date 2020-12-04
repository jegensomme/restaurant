package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.service.OrderService;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    public Order create(Order order) throws AccessException {
        logger.info("OrderController: create {} by {}", order, SecurityUtil.authUserId());
        ValidationUtil.checkAccess(SecurityUtil.authUserRole() == Role.MANAGER, SecurityUtil.authUserId());
        ValidationUtil.checkNew(order);
        try {
            return service.create(order, SecurityUtil.authUserId());
        } catch (CreateException e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("Order: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Order order, int userId) throws AccessException {
        logger.info("Order: update {} by {}", order, SecurityUtil.authUserId());
        if (order.getStatus() == OrderStatus.CLOSED) {
            SecurityUtil.checkManagerAccess();
        } else {
            SecurityUtil.checkAccess(userId);
        }
        try {
            service.update(order, userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateWithContent(Order order, int userId) throws AccessException {
        logger.info("Order: updateWithContent {} by {}", order, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        try {
            service.updateWithContent(order, userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public Order get(int id) {
        logger.info("Order: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public Order getWithContent(int id) {
        logger.info("Order: getWithContent with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.getWithContent(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<Order> getAll() {
        logger.info("Order: getAll for {}", SecurityUtil.authUserId());
        return service.getAll();
    }

    public List<Order> getAllByUser(int userId) {
        logger.info("Order: getAllByUser with userId={} for {}", userId, SecurityUtil.authUserId());
        return service.getAll();
    }

    public List<Order> getAllOpened() {
        logger.info("Order: getAllOpened for {}", SecurityUtil.authUserId());
        return service.getAllOpened();
    }

    public List<Order> getAllOpenedByUser(int userId) {
        logger.info("Order: getAllOpenedByUser with userId={} for {}", userId, SecurityUtil.authUserId());
        return service.getAllOpenedByUser(userId);
    }

    public List<Order> getAllByUserShift(int userShiftId) {
        logger.info("Order: getAllByUserShift with userShiftId={} for {}",
                userShiftId, SecurityUtil.authUserId());
        return service.getAllByUserShift(userShiftId);
    }
}
