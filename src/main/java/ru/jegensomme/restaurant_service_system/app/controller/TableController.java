package ru.jegensomme.restaurant_service_system.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.jegensomme.restaurant_service_system.app.util.SecurityUtil;
import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.service.TableService;
import ru.jegensomme.restaurant_service_system.util.exception.AccessException;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Controller
public class TableController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    public Table create(Table table) throws AccessException {
        logger.info("Table: create {} by {}", table, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            return service.create(table);
        } catch (CreateException e) {
            return null;
        }
    }

    public void delete(int id) throws AccessException {
        logger.info("Table: delete with id={} by {}", id, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Table table) throws AccessException {
        logger.info("Table: update {} by {}", table, SecurityUtil.authUserId());
        SecurityUtil.checkManagerAccess();
        try {
            service.update(table);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public Table get(int id) {
        logger.info("Table: get with id={} for {}", id, SecurityUtil.authUserId());
        try {
            return service.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<Table> getAll() {
        logger.info("Table: getAll for {}", SecurityUtil.authUserId());
        return service.getAll();
    }
}
