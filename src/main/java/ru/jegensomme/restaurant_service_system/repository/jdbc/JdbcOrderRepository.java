package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.repository.OrderRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public Order save(Order order, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Order get(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> getAllByUser(int userId) {
        return null;
    }

    /*    public static final RowMapper<Order> ORDER_MAPPER = BeanPropertyRowMapper.newInstance(Order.class);

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcOrderRepository(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("orders").usingGeneratedKeyColumns("id");
    }

    @Override
    public Order save(Order order, int userId) {
        return save(order, new MapSqlParameterSource().
                addValue("id", order.getId()).
                addValue("userId", userId).
                addValue("date_time", order.getDateTime()).
                addValue("table_number", order.getTableNumber()).
                addValue("status", order.getStatus().toString()).
                addValue("discount", order.getDiscount()));
    }

    public Order save(Order order, MapSqlParameterSource map) {
        if (order.isNew()) {
            order.setId(simpleJdbcInsert.executeAndReturnKey(map).intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE orders" +
                            " SET table_number=:table_number, status=:status, discount=:discount" +
                            " WHERE id=:id AND user_id=:userId", map) == 0) {
                return null;
            }
        }
        return order;
    }

    @Override
    public boolean delete(int id, int manager_id) {
        if (!jdbcTemplate.queryForRowSet(
                "SELECT u.id" +
                        " FROM users u JOIN user_roles r on u.id = r.user_id" +
                        " WHERE u.id=? AND r.role='MANAGER'", manager_id).next()) {
            return false;
        }
        return jdbcTemplate.update("DELETE FROM orders WHERE id=?", id) != 0;
    }

    @Override
    public Order get(int id) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(
                "SELECT o.id, o.date_time, o.table_number, o.status, o.discount" +
                        " FROM orders o" +
                        " WHERE id=?" +
                        " ORDER BY o.id, o.date_time", ORDER_MAPPER, id));
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query(
                "SELECT o.id, o.date_time, o.table_number, o.status, o.discount" +
                        " FROM orders o" +
                        " ORDER BY o.id, o.date_time", ORDER_MAPPER);
    }

    @Override
    public List<Order> getAllByUser(int userId) {
        return jdbcTemplate.query(
                "SELECT o.id, o.date_time, o.table_number, o.status, o.discount" +
                        " FROM orders o" +
                        " WHERE user_id=?" +
                        " ORDER BY o.id, o.date_time", ORDER_MAPPER, userId);
    }
 */
}
