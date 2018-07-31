package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final MealDao mealDao = new MealDaoImpl();

    private static final String LIST_VIEW = "meals";
    private static final String EDIT_VIEW = "meal";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) action = "getAll";
        int id = 0;
        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("mealId"));
                mealDao.delete(id);
            case "getAll":
                forward = LIST_VIEW;
                List<MealWithExceed> mealsWithExceed = getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT);
                request.setAttribute("meals", mealsWithExceed);
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("mealId"));
            default:
                Meal meal = (id == 0 ? new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "New meal", 100):mealDao.getById(id));
                request.setAttribute("meal", meal);
                forward = EDIT_VIEW;
        }
        response.sendRedirect(forward);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
        meal.setCalories(Integer.valueOf(req.getParameter("calories")));
        meal.setId(Integer.valueOf(req.getParameter("id")));
        if (meal.getId()==0){
            meal.setId(MealsUtil.mealsCounter.incrementAndGet());
            mealDao.addMeal(meal);
        } else {
            mealDao.update(meal);
        }
        resp.sendRedirect("meals");

    }
}
