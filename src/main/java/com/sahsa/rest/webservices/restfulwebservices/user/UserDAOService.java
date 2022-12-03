package com.sahsa.rest.webservices.restfulwebservices.user;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDAOService {


    private static List<User> users = new ArrayList<>();

    private static int counter = 0;

    static {
        users.add(new User(++counter, "sasha", LocalDate.now().minusYears(20)));
        users.add(new User(++counter, "ksenia", LocalDate.now().minusYears(19)));
        users.add(new User(++counter, "misha", LocalDate.now().minusYears(24)));
    }


    public List<User> findAll() {
        return users;
    }


    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }


    public User saveUser(User user) {
        user.setId(++counter);
        users.add(user);
        return user;
    }
}
