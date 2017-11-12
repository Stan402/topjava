package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {

    private static int userId = 1;

    public static int id() {
        return userId;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }


    public static void setUserId(int userId) {
        AuthorizedUser.userId = userId;
    }
}