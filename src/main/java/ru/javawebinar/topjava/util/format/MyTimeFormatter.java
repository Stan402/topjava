package ru.javawebinar.topjava.util.format;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyTimeFormatter implements Formatter<LocalTime> {
    private String pattern;

    public MyTimeFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return StringUtils.isEmpty(text) ? null : LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern, locale));
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return (time == null) ? "" : time.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

}
