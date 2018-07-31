package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealDaoImpl implements MealDao {

    private static Map<Integer, Meal> inMemoryStorage = new HashMap<>();

    static {
        meals.forEach(meal -> inMemoryStorage.put(meal.getId(), meal));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(inMemoryStorage.values());
    }

    @Override
    public Meal getById(int id) {
        return inMemoryStorage.get(id);
    }

    @Override
    public void addMeal(Meal meal) {
        inMemoryStorage.putIfAbsent(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        if (inMemoryStorage.containsKey(meal.getId())) {
            inMemoryStorage.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(int id) {
        inMemoryStorage.remove(id);
    }
}
