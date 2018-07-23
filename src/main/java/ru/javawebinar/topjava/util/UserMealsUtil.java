package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime
            , LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = mealList.stream()
                .collect(Collectors.toMap(m -> m.getDateTime().toLocalDate(), UserMeal::getCalories, (x, y) -> x + y));
        return mealList.stream()
                .filter(m -> isBetween(m.getDateTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories()
                        , dayCalories.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static boolean isBetween(LocalDateTime dateTime, LocalTime startTime, LocalTime endTime) {
        LocalTime time = dateTime.toLocalTime();
        return time.isAfter(startTime) && time.isBefore(endTime);
    }
}
