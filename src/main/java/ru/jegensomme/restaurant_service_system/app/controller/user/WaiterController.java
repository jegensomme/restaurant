package ru.jegensomme.restaurant_service_system.app.controller.user;

import ru.jegensomme.restaurant_service_system.service.UserService;

public class WaiterController extends UserController {

    public WaiterController(UserService service) {
        super(service);
    }
}
