package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.format.CustomDateTimeFormat;
import ru.javawebinar.topjava.util.format.MyDateFormatter;
import ru.javawebinar.topjava.util.format.MyTimeFormatter;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    // using @DateTimeFormat
//    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MealWithExceed> getBetween(
//            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
//            , @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime
//            , @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
//            , @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime
//    ) {
//
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }

    // custom formatters without annotation-driving
//    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MealWithExceed> getBetween(
//            @RequestParam(value = "startDate", required = false) String startDateStr
//            , @RequestParam(value = "startTime", required = false) String startTimeStr
//            , @RequestParam(value = "endDate", required = false) String endDateStr
//            , @RequestParam(value = "endTime", required = false) String endTimeStr
//    ) throws ParseException {
//        MyDateFormatter dateFormatter = new MyDateFormatter("yyyy-MM-dd");
//        MyTimeFormatter timeFormatter = new MyTimeFormatter("HH:mm");
//
//        LocalDate startDate = dateFormatter.parse(startDateStr, Locale.CANADA);
//        LocalDate endDate = dateFormatter.parse(endDateStr, Locale.CANADA);
//        LocalTime startTime = timeFormatter.parse(startTimeStr, Locale.CANADA);
//        LocalTime endTime = timeFormatter.parse(endTimeStr, Locale.CANADA);
//
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }

    //using annotation-driven custom formatter
    @GetMapping(value = "/filter")
    public List<MealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) @CustomDateTimeFormat LocalDate startDate
            , @RequestParam(value = "startTime", required = false) @CustomDateTimeFormat LocalTime startTime
            , @RequestParam(value = "endDate", required = false) @CustomDateTimeFormat LocalDate endDate
            , @RequestParam(value = "endTime", required = false) @CustomDateTimeFormat LocalTime endTime
    ) {

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}