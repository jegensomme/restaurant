package ru.jegensomme.restaurant_service_system.model;

import java.time.LocalDate;

public class Shift extends AbstractBaseEntity {

    private LocalDate date;

    public Shift() {
    }

    public Shift(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
