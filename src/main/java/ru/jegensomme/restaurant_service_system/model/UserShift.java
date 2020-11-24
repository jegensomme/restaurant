package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Column(name = "start_date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", columnDefinition = "timestamp check ( end_date_time >= start_date_time )")
    private LocalDateTime endDateTime;

    public UserShift() {
    }

    public UserShift(UserShift shift) {
        this(shift.id, shift.user, shift.startDateTime, shift.endDateTime);
    }

    public UserShift(Integer id, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this(id, null, startDateTime, endDateTime);
    }

    public UserShift(Integer id, User user, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(id);
        this.user = user;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User waiter) {
        this.user = waiter;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "UserShift{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
