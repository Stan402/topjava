package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.RootController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class MealController extends MealBaseController {

    @GetMapping("")
    public String getAll(Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("meals"
                , MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("mealId") int id, Model model) {
        model.addAttribute("meal", service.get(id, AuthorizedUser.id()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("mealId") int id) {
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Meal meal) {
        int userId = AuthorizedUser.id();
        if (meal.isNew()) {
            service.create(meal, userId);
        } else {
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }
}
