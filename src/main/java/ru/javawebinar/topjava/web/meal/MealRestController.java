package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        log.info("getAll for user {}", AuthorizedUser.id());
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate dStart, LocalDate dEnd, LocalTime tStart, LocalTime tEnd){
        log.info("getAll from {} to {} in time interval from {} to {} for user {}", dStart, dEnd, tStart, tEnd, AuthorizedUser.id());

        return MealsUtil.getFilteredWithExceeded(service.getFiltered(AuthorizedUser.id(),dStart, dEnd), tStart, tEnd, AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id){
        log.info(" get id {} for user {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public void delete(int id){
        log.info("delete id {} for user {}", id, AuthorizedUser.id());
        service.delete(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal){
        log.info("create {} for user {}", meal, AuthorizedUser.id());
        return service.create(meal, AuthorizedUser.id());
    }

    public void update(Meal meal, int id){
        log.info("update {} meal for user {]", meal, AuthorizedUser.id());
        service.update(meal, id,  AuthorizedUser.id());
    }
}