package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserGet() throws Exception {
        Meal meal = service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL3_ID, USER_ID);
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL_LIST_WITHOUT_MEAL3);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserDelete() throws Exception {
        service.delete(MEAL3_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> meals = service.getBetweenDates(START_DATE, END_DATE, USER_ID);
        assertMatch(meals, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID);
        assertMatch(meals, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL_LIST);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL2);
        updated.setDateTime(START_DATE_TIME);
        updated.setDescription("New description");
        updated.setCalories(333);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL2_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserUpdate() throws Exception {
        Meal updated = new Meal(MEAL2);
        updated.setDateTime(START_DATE_TIME);
        updated.setDescription("New description");
        updated.setCalories(333);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, START_DATE_TIME, "new meal", 100);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), MEAL8, MEAL7, newMeal);

    }

    @Test(expected = DuplicateKeyException.class)
    public void createDuplicate() throws Exception {
        Meal newMeal = new Meal(null, MEAL7.getDateTime(), "new meal", 100);
        Meal created = service.create(newMeal, ADMIN_ID);
    }

}