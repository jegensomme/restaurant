package ru.jegensomme.restaurant_service_system.app.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.service.UserService;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.util.List;

@Controller
public class UserController {

    private UserService service;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    public User create(User user) {
        logger.info("create {}", user);
        ValidationUtil.checkNew(user);
        return service.create(user);
    }

    public void delete(Integer id) {
        logger.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, Integer id) {
        ValidationUtil.assureIdConsistent(user, id);
        logger.info("update {} with id", id);
        service.update(user);
    }

    public User get(Integer id) {
        logger.info("get user {}", id);
        return service.get(id);
    }

    public User getByKey(String key) {
        logger.info("getByKey user {}", key);
        ValidationUtil.assureKeyIsCorrect(key);
        return service.getByKey(key);
    }

    public List<User> getAll() {
        logger.info("getAll user");
        return service.getAll();
    }


}
