package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "delete from Dish d where d.id=:id")
})
@Entity
@Table(name = "dishes")
public class Dish extends DishCategory {

    public static final String DELETE = "Dish.delete";

    @Column(name = "cost", nullable = false, columnDefinition = "integer default 0 check ( cost >= 0 )")
    @NotNull
    @Range(min = 0)
    private Integer cost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dishes_modifiers", joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "modifier_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"dish_id", "modifier_id"}, name = "dish_modifiers_unique_idx"))
    private List<DishModifier> modifiers;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.category, dish.cost, dish.modifiers);
    }

    public Dish(Integer id, String name, DishCategory category, Integer cost, DishModifier... modifiers) {
        this(id, name, category, cost, List.of(modifiers));
    }

    public Dish(Integer id, String name, DishCategory category, Integer cost, Collection<DishModifier> modifiers) {
        super(id, name, category);
        this.cost = cost;
        setModifiers(modifiers);
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<DishModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(Collection<DishModifier> modifiers) {
        this.modifiers = CollectionUtils.isEmpty(modifiers) ? Collections.EMPTY_LIST : List.copyOf(modifiers);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", category=" + category +
                ", cost=" + cost +
                ", modifiers" + modifiers +
                '}';
    }
}
