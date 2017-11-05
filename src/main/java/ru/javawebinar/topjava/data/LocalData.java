package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class LocalData {
    private static final Logger log = getLogger(LocalData.class);

    private static LocalData ourInstance = new LocalData();

    private final ConcurrentMap<Integer, Meal> meals;
    private volatile AtomicInteger counter;

    public static LocalData getInstance() {
        return ourInstance;
    }

    private LocalData() {
        meals = new ConcurrentHashMap<>();
        counter = new AtomicInteger(0);
        initTestData();
    }

    private void initTestData() {
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addOrUpdateMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        log.debug("map initialized with mock data...");
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals.values());
    }

    public void addOrUpdateMeal(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(counter.incrementAndGet());
            log.debug(String.format("New Id generated for meals: %d", meal.getId()));
        }
        meals.put(meal.getId(), meal);
        log.debug(String.format("Meal #%d is updated...", meal.getId()));
    }

    public Meal readMealById(int id) {
        log.debug(String.format("Reading meal #%d from localDB...", id));
        return meals.getOrDefault(id, null);
    }

    public void deleteById(int id) {
        if (meals.containsKey(id)) {
            meals.remove(id);
            log.debug(String.format("Meal #%d deleted...", id));
        } else {
            log.debug(String.format("Can't delete meal #%d - there is no meal with this id!", id));
        }
    }
}
