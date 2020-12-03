package ru.jegensomme.restaurant_service_system.app.util.to;

import ru.jegensomme.restaurant_service_system.model.Dish;

public class OrderDishTo {

    private Integer id;

    private Dish dish;

    private int amount;

    private String comment;

    private double cost;

    public OrderDishTo(Integer id, Dish dish, int amount, String comment, double cost) {
        this.id = id;
        this.dish = dish;
        this.amount = amount;
        this.comment = comment;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return dish.getName() + '\n' +
                "Количество: " + amount + '\n' +
                "Стоимость: " + cost + '\n' +
                '\n' + (comment == null ? "" : "Комментарий к блюду: " + comment);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
