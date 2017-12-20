package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
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
    public String getAllProcess(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/edit")
    public String editProcess(@RequestParam("mealId") int id, Model model) {
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteProcess(@RequestParam("mealId") int id) {
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/add")
    public String addProcess(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping("/filter")
    public String filterProcess(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping("/save")
    public String saveProcess(@ModelAttribute Meal meal) {

        if (meal.isNew()) {
            create(meal);
        } else {
            update(meal, meal.getId());
        }
        return "redirect:/meals";
    }
}
