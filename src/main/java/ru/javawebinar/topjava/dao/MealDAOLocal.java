package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAOLocal implements MealDAO {
    private static final Logger log = getLogger(MealDAOLocal.class);

    private final ConcurrentMap<Integer, Meal> meals;
    private volatile AtomicInteger counter;

    public MealDAOLocal(){
        meals = new ConcurrentHashMap<>();
        counter = new AtomicInteger(0);
        initTestData();
    }
    private void initTestData() {
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addOrUpdate(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        log.debug("map initialized with mock data...");
    }

    @Override
    public List<MealWithExceed> getAllWithExceed(int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(new ArrayList<>(meals.values())
                , LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    @Override
    public void deleteById(int id) {
        if (meals.containsKey(id)) {
            meals.remove(id);
            log.debug(String.format("Meal #%d deleted...", id));
        } else {
            log.debug(String.format("Can't delete meal #%d - there is no meal with this id!", id));
        }
    }

    @Override
    public void addOrUpdate(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(counter.incrementAndGet());
            log.debug(String.format("New Id generated for meals: %d", meal.getId()));
        }
        meals.put(meal.getId(), meal);
        log.debug(String.format("Meal #%d is updated...", meal.getId()));
    }

    @Override
    public Meal readOrCreate(int id) {
        log.debug(String.format("Reading meal #%d from localDB...", id));
        return meals.getOrDefault(id, new Meal());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

}
