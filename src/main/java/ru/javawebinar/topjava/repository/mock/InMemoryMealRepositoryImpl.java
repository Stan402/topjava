package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        log.info("init repository");
        new InMemoryUserRepositoryImpl(); //For testing purpose only!!!!! Need to refactor soon!
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("saving {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            if (get(meal.getId(), userId) == null)
                return null;
            else
                meal.setUserId(userId);
        }
        Meal oldMeal = repository.put(meal.getId(), meal);
        return oldMeal == null ? meal : oldMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("deleting {} for user {}", id, userId);
        Meal meal = get(id, userId);
        return meal != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("getting meal id = {}", id);
        Meal meal = repository.get(id);
        return meal == null || meal.getUserId() != userId ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getting all meal for user {}", userId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate dStart, LocalDate dEnd) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), dStart, dEnd))
                .collect(Collectors.toList());
    }
}

