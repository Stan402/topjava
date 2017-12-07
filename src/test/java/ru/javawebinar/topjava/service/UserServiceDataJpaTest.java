package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;


@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {

    @Autowired
    private MealService mealService;

    @Override
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MealTestData.MEALS);
    }

    @Override
    @Test
    public void getWithMealsNotFound() {
        thrown.expect(NotFoundException.class);
        service.getWithMeals(1);
    }

    @Test
    public void getWithMealsEmptyMeals() {
        mealService.delete(MealTestData.ADMIN_MEAL_ID, ADMIN_ID);
        mealService.delete(MealTestData.ADMIN_MEAL_ID + 1, ADMIN_ID);
        User user = service.getWithMeals(ADMIN_ID);
        assertThat(user.getMeals()).isEmpty();
    }
}
