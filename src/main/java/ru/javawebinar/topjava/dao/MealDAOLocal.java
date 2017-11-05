package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.data.LocalData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealDAOLocal implements MealDAO {

    private LocalData localDB = LocalData.getInstance();

    @Override
    public List<MealWithExceed> getAllWithExceed(int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(localDB.getMeals()
                , LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    @Override
    public void deleteById(int id) {
        localDB.deleteById(id);
    }

    @Override
    public void addOrUpdate(Meal meal) {
        localDB.addOrUpdateMeal(meal);
    }

    @Override
    public Meal readById(int id) {
        return localDB.readMealById(id);
    }

    @Override
    public List<Meal> getAll() {
        return localDB.getMeals();
    }

}
