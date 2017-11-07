package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {

    void deleteById(int id);
    void addOrUpdate(Meal meal);
    Meal readById(int id);
    List<Meal> getAll();
}
