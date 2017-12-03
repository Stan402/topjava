package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Profile({Profiles.HSQL_DB})
@Repository
public class JdbcMealRepositoryHSQLDB extends JdbcMealRepositoryImpl implements MealRepository {

    public JdbcMealRepositoryHSQLDB(DataSource dataSource, JdbcTemplate jdbcTemplate
            , NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    Timestamp convertDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

}
