package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOLocal;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String MEAL_LIST = "/meals.jsp";
    private static final String ADD_OR_UPDATE_MEAL = "/update-meal.jsp";

    private static final int CALORIES_RATION = 2000;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private MealDAO mealDAO;

    @Override
    public void init() throws ServletException {
        mealDAO = new MealDAOLocal();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        int id;
        switch (action) {
            case "delete":
                id = Integer.parseInt(req.getParameter("id"));
                mealDAO.deleteById(id);
                log.debug("redirect to meals...");
                resp.sendRedirect("meals");
                break;
            case "list":
                log.debug("forward to meals...");
                req.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDAO.getAll()
                        , LocalTime.MIN, LocalTime.MAX, CALORIES_RATION));
                req.setAttribute("formatter", formatter);
                req.getRequestDispatcher(MEAL_LIST).forward(req, resp);
                break;
            case "update":
                String idString = req.getParameter("id");
                Meal meal = (idString != null) ? mealDAO.readById(Integer.parseInt(idString)) : new Meal();
                req.setAttribute("meal", meal);
                log.debug("forward to update...");
                req.getRequestDispatcher(ADD_OR_UPDATE_MEAL).forward(req, resp);
                break;
            default:
                log.warn("unknown parametr: action - %s", action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to add/update...");
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        log.debug(String.format("doPost id=%d, localDateTime=%s, description=%s, calories=%d"
                , id, localDateTime.format(formatter), description, calories));

        Meal meal = new Meal(localDateTime, description, calories);
        meal.setId(id);
        mealDAO.addOrUpdate(meal);
        resp.sendRedirect("meals");
    }
}
