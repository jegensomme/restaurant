package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "delete from Dish d where d.id=:id")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dishes")
public class Dish extends DishCategory {

    public static final String DELETE = "Dish.delete";

    @Column(name = "cost", nullable = false, columnDefinition = "integer default 0 check ( cost >= 0 )")
    @NotNull
    @Range(min = 0)
    private double cost;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.category, dish.cost);
    }

    public Dish(Integer id, String name, DishCategory category, double cost) {
        super(id, name, category);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", category=" + category +
                ", cost=" + cost +
                '}';
    }
}
