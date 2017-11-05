package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOLocal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet{
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_RATION = 2000;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MealDAO mealDAO = new MealDAOLocal();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals...");
        req.setAttribute("formatter", formatter);
        req.setAttribute("meals", mealDAO.getAllWithExceed(CALORIES_RATION));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
