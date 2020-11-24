package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.service.UserService;
import ru.jegensomme.restaurant_service_system.service.UserShiftService;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserShiftController {

    private UserShiftService service;

    private UserService userService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public UserShiftController(UserShiftService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public UserShift create(UserShift userShift) {
        int userId = SecurityUtil.authUserId();
        ValidationUtil.checkNew(userShift);
        logger.info("create {} for {}", userShift, userId);
        return service.create(userShift, userId);
    }

    public void delete(int id) {
        int authUserId = SecurityUtil.authUserId();
        checkManagerAccess(authUserId);
        logger.info("delete {} by {}", id, authUserId);
        service.delete(id);
    }

    public void update(UserShift userShift, int userId) {
        checkAccess(userId);
        logger.info("update {} by {}", SecurityUtil.authUserId());
        service.update(userShift, userId);
    }

    public UserShift get(int id, int userId) {
        checkAccess(userId);
        logger.info("get userShift {} for {}", id, SecurityUtil.authUserId());
        return service.get(id);
    }

    public UserShift getByUserDate(int userId, LocalDate date) {
        checkAccess(userId);
        logger.info("getByUserDate {} {} userShift for {}", userId, date, SecurityUtil.authUserId());
        return service.getByUserDate(userId, date);
    }

    public List<UserShift> getAll() {
        int authUserId = SecurityUtil.authUserId();
        checkManagerAccess(authUserId);
        logger.info("get all userShift for {}", authUserId);
        return service.getAll();
    }

    public List<UserShift> getAllByUser(int userId) {
        checkAccess(userId);
        logger.info("getAllByUser {} userShift for {}", userId, SecurityUtil.authUserId());
        return service.getAllByUser(userId);
    }

    private void checkAccess(int userId) {
        int authUserId = SecurityUtil.authUserId();
        if (authUserId != userId) {
            checkManagerAccess(authUserId);
        }
    }

    private void checkManagerAccess(int userId) {
        User user = userService.get(userId);
        ValidationUtil.checkAccess(!user.getRoles().contains(Role.MANAGER), userId);
    }
}
