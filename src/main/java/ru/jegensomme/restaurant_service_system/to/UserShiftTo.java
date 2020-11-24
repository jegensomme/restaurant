package ru.jegensomme.restaurant_service_system.to;

import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.util.DateTimeUtil;

import java.time.LocalDateTime;

public class UserShiftTo {

    private final Integer id;

    private final User user;

    private final LocalDateTime startDateTime;

    private final LocalDateTime endDateTime;

    private boolean isOpen;

    public UserShiftTo(Integer id, User user, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isOpen) {
        this.id = id;
        this.user = user;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return user.getName() + " " + DateTimeUtil.toString(startDateTime) +
                " " + (isOpen ? "смена открыта" : DateTimeUtil.toString(endDateTime));
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
