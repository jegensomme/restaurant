package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.UserShift;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class UserShiftTestData {

    public static final TestMatcher<UserShift> USER_SHIFT_MATCHER = TestMatcher.usingFieldsComparator("user", "endDateTime");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int USER_SHIFT1_ID = START_SEQ + 27;
    public static final int USER_SHIFT2_ID = USER_SHIFT1_ID + 1;
    public static final int USER_SHIFT3_ID = USER_SHIFT1_ID + 2;

    public static final int USER_SHIFT4_ID = USER_SHIFT1_ID + 3;
    public static final int USER_SHIFT5_ID = USER_SHIFT1_ID + 4;
    public static final int USER_SHIFT6_ID = USER_SHIFT1_ID + 5;

    /*public static final UserShift USER_SHIFT1 = new UserShift(USER_SHIFT1_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 10, 9, 0),
            LocalDateTime.of(2020, Month.OCTOBER, 10, 21, 0));
    public static final UserShift USER_SHIFT2 = new UserShift(USER_SHIFT2_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 10, 11, 0),
            LocalDateTime.of(2020, Month.OCTOBER, 10, 23, 0));
    public static final UserShift USER_SHIFT3 = new UserShift(USER_SHIFT3_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 10, 11, 0),
            LocalDateTime.of(2020, Month.OCTOBER, 10, 23, 0));

    public static final UserShift USER_SHIFT4 = new UserShift(USER_SHIFT4_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 11, 11, 0), null);
    public static final UserShift USER_SHIFT5 = new UserShift(USER_SHIFT5_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 11, 11, 0),
            LocalDateTime.of(2020, Month.OCTOBER, 11, 23, 0));
    public static final UserShift USER_SHIFT6 = new UserShift(USER_SHIFT6_ID,
            LocalDateTime.of(2020, Month.OCTOBER, 11, 9, 0),
            LocalDateTime.of(2020, Month.OCTOBER, 11, 21, 0));

    public static UserShift getNew() {
        return new UserShift(null,
                LocalDateTime.of(2020, Month.OCTOBER, 12, 9, 0),
                LocalDateTime.of(2020, Month.OCTOBER, 12, 21, 0));
    }

    public static UserShift getUpdated() {
        UserShift updated = new UserShift(USER_SHIFT4);
        updated.setEndDateTime(LocalDateTime.of(2020, Month.OCTOBER, 11, 23, 0));
        return updated;
    }*/
}
