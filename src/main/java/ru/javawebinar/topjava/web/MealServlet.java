package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOLocal;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String MEAL_LIST = "/meals.jsp";
    private static final String ADD_OR_UPDATE_MEAL = "/update-meal.jsp";

    private static final int CALORIES_RATION = 2000;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final MealDAO mealDAO = new MealDAOLocal();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        String forward = "";
        int id;
        switch (action) {
            case "delete":
                id = Integer.parseInt(req.getParameter("id"));
                mealDAO.deleteById(id);
                req.setAttribute("meals", mealDAO.getAllWithExceed(CALORIES_RATION));
                forward = MEAL_LIST;
                break;
            case "list":
                req.setAttribute("meals", mealDAO.getAllWithExceed(CALORIES_RATION));
                forward = MEAL_LIST;
                break;
            case "update":
                id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("meal", mealDAO.readById(id));
                forward = ADD_OR_UPDATE_MEAL;
                break;
            case "add":
                req.setAttribute("meal", new Meal());
                forward = ADD_OR_UPDATE_MEAL;
                break;
            default:
                log.warn("unknown parametr: action - %s", action);
        }

        log.debug("forward to meals...");
        req.setAttribute("formatter", formatter);
        req.getRequestDispatcher(forward).forward(req, resp);
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
