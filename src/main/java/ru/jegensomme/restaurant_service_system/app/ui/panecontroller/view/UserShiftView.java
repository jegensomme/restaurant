package ru.jegensomme.restaurant_service_system.app.ui.panecontroller.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserShiftView {

    private SimpleIntegerProperty id;

    private SimpleObjectProperty<LocalDate> date;

    private SimpleObjectProperty<LocalTime> startTime;

    private SimpleObjectProperty<LocalTime> endTime;

    private SimpleDoubleProperty sales;

    public UserShiftView(Integer id, LocalDate date, LocalTime startTime, LocalTime endTime, double sales) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.sales = new SimpleDoubleProperty(sales);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public LocalTime getStartTime() {
        return startTime.get();
    }

    public SimpleObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }

    public LocalTime getEndTime() {
        return endTime.get();
    }

    public SimpleObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime.set(endTime);
    }

    public double getSales() {
        return sales.get();
    }

    public SimpleDoubleProperty salesProperty() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales.set(sales);
    }
}
