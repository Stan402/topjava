package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.data.LocalData;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealDAOLocal implements MealDAO {

    @Override
    public List<MealWithExceed> getAllWithExceed(int caloriesPerDay) {

        return MealsUtil.getFilteredWithExceeded(LocalData.getInstance().getMeals()
                , LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
