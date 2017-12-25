package ru.javawebinar.topjava.util.format;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyDateFormatter implements Formatter<LocalDate> {
    private String pattern;

    public MyDateFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return StringUtils.isEmpty(text) ? null : LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern, locale));
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return (date == null) ? "" : date.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

}
