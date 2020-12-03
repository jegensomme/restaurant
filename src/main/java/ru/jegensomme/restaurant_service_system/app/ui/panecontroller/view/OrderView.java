package ru.jegensomme.restaurant_service_system.app.ui.panecontroller.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class OrderView {

    SimpleIntegerProperty id;

    SimpleObjectProperty<LocalDateTime> dateTime;

    SimpleIntegerProperty tableNumber;

    SimpleDoubleProperty checkAmount;

    public OrderView(int id, LocalDateTime dateTime, int tableNumber, double checkAmount) {
        this.id = new SimpleIntegerProperty(id);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
        this.tableNumber = new SimpleIntegerProperty(tableNumber);
        this.checkAmount = new SimpleDoubleProperty(checkAmount);
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

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.set(dateTime);
    }

    public int getTableNumber() {
        return tableNumber.get();
    }

    public SimpleIntegerProperty tableNumberProperty() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber.set(tableNumber);
    }

    public double getCheckAmount() {
        return checkAmount.get();
    }

    public SimpleDoubleProperty checkAmountProperty() {
        return checkAmount;
    }

    public void setCheckAmount(double checkAmount) {
        this.checkAmount.set(checkAmount);
    }
}
