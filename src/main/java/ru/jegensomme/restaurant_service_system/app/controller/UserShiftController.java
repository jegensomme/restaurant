package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.service.UserShiftService;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserShiftController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserShiftService service;

    public UserShiftController(UserShiftService service) {
        this.service = service;
    }

    public UserShift create(UserShift userShift) {
        logger.info("UserShift: create {} by {}", userShift, SecurityUtil.authUserId());
        ValidationUtil.checkNew(userShift);
        try {
            return service.create(userShift, SecurityUtil.authUserId());
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("UserShift: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(UserShift userShift, int userId) throws AccessException {
        logger.info("UserShift: update {} by {}", userShift, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        try {
            service.update(userShift, userId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public UserShift get(int id, int userId) throws AccessException {
        logger.info("UserShift: get with id={} for {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<UserShift> getAll() throws AccessException {
        logger.info("UserShift: getAll for {}", SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        return service.getAll();
    }

    public List<UserShift> getAllByUser(int userId) throws AccessException {
        logger.info("UserShift: getAllByUser with userId={} for {}", userId, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        return service.getAllByUser(userId);
    }

    public List<UserShift> getOpenedByUser(int userId) throws AccessException {
        logger.info("UserShift: getOpenedByUser with userId={} for {}", userId, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        return service.getOpenedByUser(userId);
    }

    public List<UserShift> getBetweenInclusiveByUser(int userId, LocalDate startDate, LocalDate endDate) throws AccessException {
        logger.info("UserShift: getBetweenInclusiveByUser with userId={}" +
                " with startDate={} with endDate={} for {}", userId, startDate, endDate, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        return service.getBetweenInclusiveByUser(userId, startDate, endDate);
    }

    public Float getTotalSalesByUserShift(int id, int userId) throws AccessException {
        logger.info("UserShift: getTotalSalesByUserShift with id={} userId={} for {}", id, userId, SecurityUtil.authUserId());
        SecurityUtil.checkAccess(userId);
        return service.getTotalSalesByUserShift(id);
    }
}
