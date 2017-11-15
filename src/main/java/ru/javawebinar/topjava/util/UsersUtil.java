package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("User5", "user1@gmail.com", "Pass1", Role.ROLE_ADMIN),
            new User("User2", "user2@gmail.com", "Pass2", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User("User3", "user3@gmail.com", "Pass3", Role.ROLE_USER)
    );

}
