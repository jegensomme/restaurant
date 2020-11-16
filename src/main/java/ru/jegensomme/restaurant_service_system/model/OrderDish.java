package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.Hibernate;

import java.util.Objects;

public class OrderDish {

    private Order order;

    private Dish dish;

    private int amount;

    private String comment;

    public OrderDish(Order order, Dish dish, int amount, String comment) {
        this.order = order;
        this.dish = dish;
        this.amount = amount;
        this.comment = comment;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Dish getDish() {
        return dish;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        OrderDish that = (OrderDish) o;
        return that.order.id() == order.id() && that.dish.id() == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, dish);
    }
}
