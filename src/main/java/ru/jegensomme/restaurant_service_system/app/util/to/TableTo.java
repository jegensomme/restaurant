package ru.jegensomme.restaurant_service_system.app.util.to;

public class TableTo {

    private final Integer id;

    private final Integer number;

    private final int seats;

    public TableTo(Integer id, Integer number, int seats) {
        this.id = id;
        this.number = number;
        this.seats = seats;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
