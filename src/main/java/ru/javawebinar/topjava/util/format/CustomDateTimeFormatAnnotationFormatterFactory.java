package ru.javawebinar.topjava.util.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class CustomDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormat> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<Class<?>>();
        setTypes.add(LocalDate.class);
        setTypes.add(LocalTime.class);
        return setTypes;
    }

    @Override
    public Printer<?> getPrinter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return fieldType == LocalDate.class ? new MyDateFormatter(DATE_PATTERN) : new MyTimeFormatter(TIME_PATTERN);
    }

    @Override
    public Parser<?> getParser(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return fieldType == LocalDate.class ? new MyDateFormatter(DATE_PATTERN) : new MyTimeFormatter(TIME_PATTERN);
    }
}
