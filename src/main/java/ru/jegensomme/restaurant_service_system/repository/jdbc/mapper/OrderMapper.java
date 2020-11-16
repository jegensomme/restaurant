package ru.jegensomme.restaurant_service_system.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.to.DishTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public List<Order> mapRowSet(SqlRowSet rowSet) {
        if (!rowSet.next()) {
            return null;
        }
        List<Order> orders = new ArrayList<>();
        boolean isEnd = false;
        while(!isEnd) {
            Integer id             = rowSet.getInt(1);
            LocalDateTime dateTime = rowSet.getTimestamp(2).toLocalDateTime();
            int tableNumber        = rowSet.getInt(3);
            Integer discount       = rowSet.getInt(4);
            OrderStatus status     = OrderStatus.valueOf(rowSet.getString(5));
            List<OrderDish> content   = new ArrayList<>();
            while (!isEnd) {
                if (rowSet.getInt(1) != id) {
                    break;
                }
                content.add(new OrderDish(new Order(id), new Dish(rowSet.getInt(6)), rowSet.getInt(7), rowSet.getString(8)));
                isEnd = !rowSet.next();
            }
            orders.add(new Order(id, dateTime, tableNumber, status, discount, content));
        }
        return orders;
    }
}
