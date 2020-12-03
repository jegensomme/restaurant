package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.service.UserService;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    public void authUser() {
        logger.info("User: authUser {}", SecurityUtil.authUserId());
        service.authUser(SecurityUtil.authUserRole());
    }

    public User create(User user) {
        logger.info("User: create {} by {}", user, SecurityUtil.authUserId());
        ValidationUtil.checkNew(user);
        try {
            return service.create(user);
        } catch (CreateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Integer id) {
        logger.info("User: delete with id={} by {}", id, SecurityUtil.authUserId());
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        logger.info("User: update {} by {}", user, SecurityUtil.authUserId());
        try {
            service.update(user);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public User get(Integer id) {
        logger.info("User: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getByKey(String key) {
        logger.info("User: getByKey with key={} for {}", key, SecurityUtil.authUserId());
        ValidationUtil.assureKeyIsCorrect(key);
        try {
            return service.getByKey(key);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<User> getAll() {
        logger.info("User: getAll for {}", SecurityUtil.authUserId());
        return service.getAll();
    }

    public List<User> getAllByRole(Role role) {
        logger.info("User: getAllByRole with role={} for {}", role, SecurityUtil.authUserId());
        return service.getAllByRole(role);
    }

    public List<User> getAllByShiftDate(LocalDate date) {
        logger.info("User: getAllByShiftDate with date={} for {}",date, SecurityUtil.authUserId());
        return service.getAllByShiftDate(date);
    }

}
