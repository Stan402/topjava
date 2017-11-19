package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {


    public static final int MEAL1_ID = UserTestData.ADMIN_ID + 1;
    public static final int MEAL2_ID = UserTestData.ADMIN_ID + 2;
    public static final int MEAL3_ID = UserTestData.ADMIN_ID + 3;
    public static final int MEAL4_ID = UserTestData.ADMIN_ID + 4;
    public static final int MEAL5_ID = UserTestData.ADMIN_ID + 5;
    public static final int MEAL6_ID = UserTestData.ADMIN_ID + 6;
    public static final int MEAL7_ID = UserTestData.ADMIN_ID + 7;
    public static final int MEAL8_ID = UserTestData.ADMIN_ID + 8;

    public static final LocalDate START_DATE = LocalDate.of(2015, Month.MAY, 30);
    public static final LocalDate END_DATE = LocalDate.of(2015, Month.MAY, 30);
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 11, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 31, 12, 0);


    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL3_ID , LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(MEAL6_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal MEAL7 = new Meal(MEAL7_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 1500);
    public static final Meal MEAL8 = new Meal(MEAL8_ID, LocalDateTime.of(2015, Month.JUNE, 1, 20, 0), "Админ ужин", 510);

    public static final List<Meal> USER_MEAL_LIST = Stream.of(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> USER_MEAL_LIST_WITHOUT_MEAL3 = Stream.of(MEAL1, MEAL2, MEAL4, MEAL5, MEAL6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
