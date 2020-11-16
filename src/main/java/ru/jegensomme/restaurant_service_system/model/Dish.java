package ru.jegensomme.restaurant_service_system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dish extends DishCategory {

    private Integer cost;

    public Dish() {
    }

    public Dish(Integer id) {
        super(id);
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.groupId, dish.cost);
    }

    public Dish(Integer id, String name, Integer groupId, Integer cost) {
        super(id, name, groupId);
        this.cost = cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", groupId=" + groupId +
                ", cost=" + cost +
                '}';
    }
}
