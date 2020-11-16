package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.model.OrderDish;
import ru.jegensomme.restaurant_service_system.model.OrderStatus;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;
import ru.jegensomme.restaurant_service_system.repository.jdbc.mapper.OrderMapper;
import ru.jegensomme.restaurant_service_system.to.DishTo;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    public static final OrderMapper ORDER_MAPPER = new OrderMapper();

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private OrderContentRepository orderContentRepository;

    private OrderStatusRepository orderStatusRepository;

    private OrderDiscountRepository orderDiscountRepository;

    public JdbcOrderRepository(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("orders").usingGeneratedKeyColumns("id");
        this.orderContentRepository  = new OrderContentRepository(dataSource);
        this.orderStatusRepository   = new OrderStatusRepository(dataSource);
        this.orderDiscountRepository = new OrderDiscountRepository(dataSource);
    }

    @Override
    public Order save(Order order, int waiter_id) {
        return save(order, new MapSqlParameterSource().
                addValue("id", order.getId()).
                addValue("waiter_id", waiter_id).
                addValue("date_time", order.getDateTime()).
                addValue("table_number", order.getTableNumber()));
    }

    public Order save(Order order, MapSqlParameterSource map) {
        if (order.isNew()) {
            order.setId(simpleJdbcInsert.executeAndReturnKey(map).intValue());
            orderStatusRepository.create(order.id(), order.getStatus());
            orderDiscountRepository.create(order.id(), order.getDiscount());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE orders" +
                            " SET table_number=:table_number" +
                            " WHERE id=:id AND waiter_id=:waiter_id", map) == 0) {
                return null;
            }
            if (!orderStatusRepository.update(order.id(), order.getStatus())) {
                return null;
            }
            if (!orderDiscountRepository.update(order.id(), order.getDiscount())) {
                return null;
            }
        }
        return orderContentRepository.save(order.id(), order.getContent()) ? order : null;
    }

    @Override
    public boolean delete(int id, int manager_id) {
        if (!jdbcTemplate.queryForRowSet(
                "SELECT u.id" +
                        " FROM users u JOIN roles r on u.id = r.user_id" +
                        " WHERE u.id=? AND r.role='MANAGER'", manager_id).next()) {
            return false;
        }
        return jdbcTemplate.update("DELETE FROM orders WHERE id=?", id) != 0;
    }

    @Override
    public Order get(int id) {
        return DataAccessUtils.singleResult(ORDER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT o.id, o.date_time, o.table_number, od.discount, os.status, oc.dish_id, oc.amount, oc.comment" +
                        " FROM orders o" +
                        " LEFT JOIN order_discount od on o.id = od.order_id" +
                        " LEFT JOIN order_status os on o.id = os.order_id" +
                        " LEFT JOIN order_content oc on o.id = oc.order_id" +
                        " WHERE id=?" +
                        " ORDER BY o.id, o.date_time, oc.dish_id", id)));
    }

    @Override
    public List<Order> getAll() {
        return ORDER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT o.id, o.date_time, o.table_number, od.discount, os.status, oc.dish_id, oc.amount, oc.comment" +
                        " FROM orders o" +
                        " LEFT JOIN order_discount od on o.id = od.order_id" +
                        " LEFT JOIN order_status os on o.id = os.order_id" +
                        " LEFT JOIN order_content oc on o.id = oc.order_id" +
                        " ORDER BY o.id, o.date_time, oc.dish_id"));
    }

    @Override
    public List<Order> getAllByWaiter(int waiterId) {
        return ORDER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT o.id, o.date_time, o.table_number, od.discount, os.status, oc.dish_id, oc.amount, oc.comment" +
                        " FROM orders o" +
                        " LEFT JOIN order_discount od on o.id = od.order_id" +
                        " LEFT JOIN order_status os on o.id = os.order_id" +
                        " LEFT JOIN order_content oc on o.id = oc.order_id" +
                        " WHERE waiter_id=?" +
                        " ORDER BY o.id, o.date_time, oc.dish_id", waiterId));
    }

    private class OrderDiscountRepository {
        SimpleJdbcInsert simpleJdbcInsert;

        OrderDiscountRepository(DataSource dataSource) {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("order_discount");
        }

        boolean create(int orderId, int discount) {
            return simpleJdbcInsert.execute(new MapSqlParameterSource().
                    addValue("order_id", orderId).
                    addValue("discount", discount)) != 0;
        }

        int get(int orderId) {
            return jdbcTemplate.queryForObject(
                    "SELECT discount FROM order_discount WHERE order_id=?", Integer.class, orderId);
        }

        boolean update(int orderId, int discount) {
            return jdbcTemplate.update(
                    "UPDATE order_discount SET discount=? WHERE order_id=?", discount, orderId) != 0;
        }

        boolean delete(int orderId) {
            return jdbcTemplate.update("DELETE FROM order_discount WHERE order_id=?", orderId) != 0;
        }
    }

    private class OrderStatusRepository {
        SimpleJdbcInsert simpleJdbcInsert;

        OrderStatusRepository(DataSource dataSource) {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("order_status");
        }

        boolean create(int orderId, OrderStatus status) {
            return simpleJdbcInsert.execute(new MapSqlParameterSource().
                    addValue("order_id", orderId).
                    addValue("status", status.toString())) != 0;
        }

        OrderStatus get(int orderId) {
            return OrderStatus.valueOf(jdbcTemplate.queryForObject(
                    "SELECT status FROM order_status WHERE order_id=?", String.class, orderId));
        }

        boolean update(int orderId, OrderStatus status) {
            return jdbcTemplate.update(
                    "UPDATE order_status SET status=? WHERE order_id=?", status.toString(), orderId) != 0;
        }

        boolean delete(int orderId) {
            return jdbcTemplate.update("DELETE FROM order_status WHERE order_id=?", orderId) != 0;
        }
    }

    private class OrderContentRepository {
        RowMapper<OrderDish> rowMapper = BeanPropertyRowMapper.newInstance(OrderDish.class);

        SimpleJdbcInsert simpleJdbcInsert;

        OrderContentRepository(DataSource dataSource) {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("order_content");
        }

        boolean save(int orderId, OrderDish dish, OrderDish... other) {
            List<OrderDish> content = List.of(other);
            content.add(dish);
            return save(orderId, content);
        }

        boolean save(int orderId, List<OrderDish> content) {
            deleteAll(orderId);
            for (OrderDish dish : content) {
                if (!save(orderId, dish)) {
                    return false;
                }
            }
            return true;
        }

        boolean save(int orderId, OrderDish dish) {
            MapSqlParameterSource map = new MapSqlParameterSource().
                    addValue("order_id", orderId).
                    addValue("dish_id", dish.getDish().id()).
                    addValue("amount",  dish.getAmount()).
                    addValue("comment", dish.getComment());
            return simpleJdbcInsert.execute(map) != 0;
        }

        List<OrderDish> get(int orderId) {
            return jdbcTemplate.query(
                    "SELECT dish_id, amount, comment" +
                            " FROM order_content" +
                            " WHERE order_id=?", rowMapper, orderId);
        }

        boolean delete(int orderId, OrderDish dish, OrderDish... other) {
            List<OrderDish> content = List.of(other);
            content.add(dish);
            return delete(orderId, content);
        }

        boolean delete(int orderId, List<OrderDish> content) {
            for (OrderDish dish : content) {
                if (!delete(orderId, dish.getDish().id())) {
                    return false;
                }
            }
            return true;
        }

        boolean delete(int orderId, int dishId) {
            return jdbcTemplate.update(
                    "DELETE FROM order_content" +
                            " WHERE order_id=? and dish_id=?", orderId, dishId) != 0;
        }

        void deleteAll(int orderId) {
            jdbcTemplate.update("DELETE FROM order_content WHERE order_id=?", orderId);
        }
    }
}
