package ru.jegensomme.restaurant_service_system.util;

import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.to.UserShiftTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserShiftUtil {

    private UserShiftUtil() {
    }

    public static List<UserShiftTo> getTos(Collection<UserShift> userShifts) {
        return filterByPredicate(userShifts, userShift -> true);
    }

    public static List<UserShiftTo> filterByPredicate(Collection<UserShift> userShifts, Predicate<UserShift> filter) {
        return userShifts.stream()
                .filter(filter)
                .map(userShift -> createTo(userShift, userShift.getEndDateTime() == null))
                .collect(Collectors.toList());
    }

    private static UserShiftTo createTo(UserShift userShift, boolean isOpen) {
        return new UserShiftTo(userShift.getId(), userShift.getUser(), userShift.getStartDateTime(), userShift.getEndDateTime(), isOpen);
    }
}
