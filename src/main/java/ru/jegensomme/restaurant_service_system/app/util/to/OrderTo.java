package ru.jegensomme.restaurant_service_system.app.util.to;

import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.util.DateTimeUtil;

import java.time.LocalDateTime;

public class OrderTo {

    private final Integer id;

    private final User user;

    private final LocalDateTime dateTime;

    private final Table table;

    private final OrderStatus status;

    private final int discount;

    public OrderTo(Integer id, User user, LocalDateTime dateTime, Table table, OrderStatus status, int discount) {
        this.id = id;
        this.user = user;
        this.dateTime = dateTime;
        this.table = table;
        this.status = status;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Дата и время заказа" + DateTimeUtil.toString(dateTime) + "\n" +
                "Стол " + table.getNumber() + "\n" +
                "Статус " + status;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Table getTable() {
        return table;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getDiscount() {
        return discount;
    }
}
