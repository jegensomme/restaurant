package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator("shifts", "orders");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int WAITER1_ID = START_SEQ;
    public static final int MANAGER_ID = START_SEQ + 1;
    public static final int WAITER2_ID = START_SEQ + 2;

    public static final String NOT_FOUND_KEY = "9999";

    public static final String WAITER_KEY  = "1111";
    public static final String MANAGER_KEY = "2222";
    public static final String WAITER2_KEY  = "3333";

    public static final User WAITER1 =  new User(WAITER1_ID, "Waiter1", WAITER_KEY,   Role.WAITER);
    public static final User MANAGER =  new User(MANAGER_ID, "Manager", MANAGER_KEY,  Role.MANAGER);
    public static final User WAITER2 =  new User(WAITER2_ID, "Waiter2", WAITER2_KEY,  Role.WAITER);

    public static User getNew() {
        return new User(null, "New", "4444", Role.WAITER);
    }

    public static User getUpdated() {
        User updated = new User(WAITER1);
        updated.setName("Updated");
        return updated;
    }
}
