package ru.jegensomme.restaurant_service_system;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.jegensomme.restaurant_service_system.model.Order;
import ru.jegensomme.restaurant_service_system.repository.jdbc.mapper.OrderMapper;
import ru.jegensomme.restaurant_service_system.testdata.OrderTestData;

import javax.sql.DataSource;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-app.xml", "classpath*:spring/spring-db.xml");
        DataSource dataSource = context.getBean(DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        OrderMapper mapper = new OrderMapper();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                "SELECT o.id, o.date_time, o.table_number, od.discount, os.status, oc.dish_id, oc.amount, oc.comment" +
                        " FROM orders o" +
                        " LEFT JOIN order_discount od on o.id = od.order_id" +
                        " LEFT JOIN order_status os on o.id = os.order_id" +
                        " LEFT JOIN order_content oc on o.id = oc.order_id" +
                        " WHERE o.id=?" +
                        " ORDER BY o.id", OrderTestData.NOT_FOUND);

        List<Order> orders = mapper.mapRowSet(rowSet);

        mapper.mapRowSet(jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = 10"));
    }
}
