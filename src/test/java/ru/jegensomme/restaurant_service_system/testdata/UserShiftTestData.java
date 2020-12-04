package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.UserShift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class UserShiftTestData {

    public static final TestMatcher<UserShift> USER_SHIFT_MATCHER = TestMatcher.usingFieldsComparator("user", "endDateTime");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int USER_SHIFT1_ID = START_SEQ + 25;
    public static final int USER_SHIFT2_ID = USER_SHIFT1_ID + 1;
    public static final int USER_SHIFT3_ID = USER_SHIFT1_ID + 2;

    public static final int USER_SHIFT4_ID = USER_SHIFT1_ID + 3;
    public static final int USER_SHIFT5_ID = USER_SHIFT1_ID + 4;
    public static final int USER_SHIFT6_ID = USER_SHIFT1_ID + 5;

    public static final UserShift USER_SHIFT1 = new UserShift(USER_SHIFT1_ID,
            LocalDate.of(2020, Month.OCTOBER, 10),
            LocalTime.of(9, 0),
            LocalTime.of(21, 0));
    public static final UserShift USER_SHIFT2 = new UserShift(USER_SHIFT2_ID,
            LocalDate.of(2020, Month.OCTOBER, 10),
            LocalTime.of(11, 0),
            LocalTime.of(23, 0));
    public static final UserShift USER_SHIFT3 = new UserShift(USER_SHIFT3_ID,
            LocalDate.of(2020, Month.OCTOBER, 10),
            LocalTime.of(11, 0),
            LocalTime.of(23, 0));

    public static final UserShift USER_SHIFT4 = new UserShift(USER_SHIFT4_ID,
            LocalDate.of(2020, Month.OCTOBER, 11),
            LocalTime.of(11, 0),
            null);
    public static final UserShift USER_SHIFT5 = new UserShift(USER_SHIFT5_ID,
            LocalDate.of(2020, Month.OCTOBER, 11),
            LocalTime.of(11, 0),
            LocalTime.of(23, 0));
    public static final UserShift USER_SHIFT6 = new UserShift(USER_SHIFT6_ID,
            LocalDate.of(2020, Month.OCTOBER, 11),
            LocalTime.of(9, 0),
            LocalTime.of(21, 0));

    public static UserShift getNew() {
        return new UserShift(null,
                LocalDate.of(2020, Month.DECEMBER, 1),
                LocalTime.of(9, 0),
                null);
    }

    public static UserShift getUpdated() {
        UserShift updated = new UserShift(USER_SHIFT4);
        updated.setEndTime(LocalTime.of(23, 0));
        return updated;
    }
}
