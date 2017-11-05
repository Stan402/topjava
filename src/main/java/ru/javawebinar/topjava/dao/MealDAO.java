package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDAO {

    List<MealWithExceed> getAllWithExceed(int caloriesPerDay);
}
