package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

public class UserTestData {

    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator();

    public static final int NOT_FOUND = 10;

    public static final int WAITER_ID   = 1;
    public static final int MANAGER_ID  = 2;
    public static final int WAITER2_ID   = 3;

    public static final String NOT_FOUND_KEY = "9999";

    public static final String WAITER_KEY  = "1111";
    public static final String MANAGER_KEY = "2222";
    public static final String WAITER2_KEY  = "3333";

    public static final User WAITER =   new User(WAITER_ID,   "Waiter",   WAITER_KEY,   Role.WAITER);
    public static final User MANAGER =  new User(MANAGER_ID,  "Manager",  MANAGER_KEY,  Role.MANAGER);
    public static final User WAITER2 =  new User(WAITER2_ID,  "Waiter2",  WAITER2_KEY,  Role.WAITER);

    public static User getNew() {
        return new User(null, "New", "4444", Role.WAITER);
    }

    public static User getUpdated() {
        User updated = new User(WAITER);
        updated.setName("Updated");
        return updated;
    }
}
