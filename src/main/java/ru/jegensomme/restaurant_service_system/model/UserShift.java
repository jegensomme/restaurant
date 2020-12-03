package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = UserShift.DELETE, query = "delete from UserShift us where us.id=:id")
})
@Entity
@Table(name = "user_shifts")
public class UserShift extends AbstractBaseEntity {

    public static final String DELETE = "UserShift.delete";

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    @NotNull
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public UserShift() {
    }

    public UserShift(UserShift shift) {
        this(shift.id, shift.user, shift.date, shift.startTime, shift.endTime);
    }

    public UserShift(Integer id, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this(id, null, date, startTime, endTime);
    }

    public UserShift(Integer id, User user, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super(id);
        this.user = user;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User waiter) {
        this.user = waiter;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserShift{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
