package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            saveRoles(user);
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else {
            deleteRoles(user);
            saveRoles(user);
        }
        return user;
    }

    private void deleteRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
    }

    private void saveRoles(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        int userId = user.getId();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)"
                    , new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, userId);
                            ps.setString(2, roles.get(i).toString());
                        }

                        @Override
                        public int getBatchSize() {
                            return roles.size();
                        }
                    });
        }

    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT users.*, user_roles.role FROM users LEFT OUTER JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email "
                , new ResultSetExtractor<List<User>>() {
                    @Nullable
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        User user = null;
                        Map<Integer, User> map = new HashMap<>();
                        while (rs.next()) {
                            Integer id = rs.getInt("id");
                            user = map.get(id);
                            if (user == null) {

                                user = new User(id
                                        , rs.getString("name")
                                        , rs.getString("email")
                                        , rs.getString("password")
                                        , rs.getInt("calories_per_day")
                                        , rs.getBoolean("enabled")
                                        , rs.getDate("registered")
                                        , EnumSet.of(Role.valueOf(rs.getString("role"))));
                                map.put(id, user);
                            } else {
                                Set<Role> roles = user.getRoles();
                                roles.add(Role.valueOf(rs.getString("role")));
                                user.setRoles(roles);
                            }
                        }
                        return map.values().stream().sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail)).collect(Collectors.toList());
                    }
                });
    }

    private User setRoles(User user) {
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?"
                    , (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
