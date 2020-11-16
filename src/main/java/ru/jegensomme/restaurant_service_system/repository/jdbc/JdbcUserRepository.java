package ru.jegensomme.restaurant_service_system.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.jegensomme.restaurant_service_system.repository.jdbc.mapper.UserMapper;

import javax.sql.DataSource;

import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    public static final UserMapper USER_MAPPER = new UserMapper();

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private JdbcRoleRepository roleRepository;

    @Autowired
    public JdbcUserRepository(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                withTableName("users").usingGeneratedKeyColumns("id");
        this.roleRepository = new JdbcRoleRepository(dataSource);
    }

    @Override
    public User save(User user) {
        return save(user, new MapSqlParameterSource().
                addValue("id", user.getId()).
                addValue("key", user.getKey()).
                addValue("name", user.getName()));
    }

    public User save(User user, MapSqlParameterSource map) {
        if (user.isNew()) {
            user.setId(simpleJdbcInsert.executeAndReturnKey(map).intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET key=:key, name=:name WHERE id=:id", map) == 0) {
                return null;
            }
        }
        return roleRepository.save(user.id(), user.getRoles()) ? user : null;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return DataAccessUtils.singleResult(USER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT u.id, u.name, u.key, r.role FROM users u" +
                        " LEFT JOIN roles r on u.id = r.user_id  WHERE id=?", id)));
    }

    @Override
    public User getByKey(String key) {
        return DataAccessUtils.singleResult(USER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT u.id, u.name, u.key, r.role FROM users u" +
                        " LEFT JOIN roles r on u.id = r.user_id  WHERE key=?", key)));
    }

    @Override
    public List<User> getAll() {
        return USER_MAPPER.mapRowSet(jdbcTemplate.queryForRowSet(
                "SELECT u.id, u.name, u.key, r.role FROM users u" +
                        " LEFT JOIN roles r on u.id = r.user_id" +
                        " ORDER BY u.name ASC"));
    }

    private class JdbcRoleRepository {

        SimpleJdbcInsert simpleJdbcInsert;
        RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);

        JdbcRoleRepository(DataSource dataSource) {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource).
                    withTableName("roles");
        }

        boolean save(int userId, Role role, Role... other) {
            Set<Role> roles = EnumSet.of(role, other);
            return save(userId, roles);
        }

        boolean save(int userId, Set<Role> roles) {
            deleteAll(userId);
            for (Role role : roles) {
                if (!save(userId, role)) {
                    return false;
                }
            }
            return true;
        }

        boolean save(int userId, Role role) {
            return simpleJdbcInsert.execute(new MapSqlParameterSource().
                    addValue("user_id", userId).
                    addValue("role", role.toString())) != 0;
        }

        Set<Role> get(int userId) {
            return EnumSet.copyOf(
                    jdbcTemplate.query("SELECT role FROM roles WHERE user_id=?", rowMapper, userId));

        }

        boolean delete(int userId, Role role, Role... other) {
            Set<Role> roles = EnumSet.of(role, other);
            return delete(userId, roles);
        }

        boolean delete(int userId, Set<Role> roles) {
            for (Role role : roles) {
                if (!delete(userId, role)) {
                    return false;
                }
            }
            return true;
        }

        boolean delete(int userId, Role role) {
            return jdbcTemplate.update("DELETE FROM roles WHERE user_id=:? and role=:?", userId, role) != 0;
        }

        void deleteAll(int userId) {
            jdbcTemplate.update("DELETE FROM roles WHERE user_id=?", userId);
        }
    }
}
