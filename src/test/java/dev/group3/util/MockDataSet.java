package dev.group3.util;

import java.util.ArrayList;
import java.util.List;

import dev.group3.model.User;
import dev.group3.model.enums.userType;

public class MockDataSet {

    private static List<User> userTestSet;

    // === SETUP ===

    private static void setupUserTestSet() {
        userTestSet = new ArrayList<User>();
        userTestSet.add(new User("owner",       "secret",   "Wolf",     "Flow",     userType.OWNER,        "5555555555",   100.00));
        userTestSet.add(new User("email1",      "pass1",    "Alice",    "Apple",    userType.CUSTOMER,     "1234567890",   1000.00));
        userTestSet.add(new User("email2",      "pass2",    "Bob",      "Bacon",    userType.CUSTOMER,     "1112223333",   100.00));
        userTestSet.add(new User("email3",      "pass3",    "Carl",     "Cake",     userType.CUSTOMER,     "4445556666",   10.00));
        userTestSet.add(new User("email4",      "pass4",    "David",    "Dumpling", userType.CUSTOMER,     "7778889999",   0.00));
        userTestSet.add(new User("email5",      "pass5",    "Evelyn",   "Eggnog",   userType.CUSTOMER,     "2224448888",   1000.00));
        userTestSet.add(new User("dogLover",    "pass6",    "Frank",    "Fudge",    userType.CUSTOMER,     "2358132134",   3.92));
    }

    // === GETTERS ===

    public static List<User> getUserTestSet() {
        setupUserTestSet();
        return userTestSet;
    }
}
