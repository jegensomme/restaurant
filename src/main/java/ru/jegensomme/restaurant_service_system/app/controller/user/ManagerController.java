package ru.jegensomme.restaurant_service_system.app.controller.user;

import ru.jegensomme.restaurant_service_system.service.UserService;

public class ManagerController extends UserController {

    public ManagerController(UserService service) {
        super(service);
    }
}
