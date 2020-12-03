package ru.jegensomme.restaurant_service_system.app.util.to;

import ru.jegensomme.restaurant_service_system.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserShiftTo {

    private final Integer id;

    private final User user;

    private final LocalDate date;

    private final LocalTime startTime;

    private final LocalTime endTime;

    private final double sales;

    public UserShiftTo(Integer id, User user, LocalDate date, LocalTime startTime, LocalTime endTime, double sales) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sales = sales;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public double getSales() {
        return sales;
    }
}
