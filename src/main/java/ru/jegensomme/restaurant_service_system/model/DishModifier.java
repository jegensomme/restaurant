package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = DishModifier.DELETE, query = "delete from DishModifier dm where dm.id=:id")
})
@Entity
@Table(name = "modifiers")
public class DishModifier extends AbstractNamedEntity {

    public static final String DELETE = "DishModifier.delete";

    @Column(name = "min_value", nullable = false, columnDefinition = "integer default 1 check ( min_value >= 0 )")
    @NotNull
    @Range(min = 0)
    private Integer minValue;

    @Column(name = "max_value", columnDefinition = "integer check ( max_value >= min_value )")
    @Range(min = 0)
    private Integer maxValue;

    public DishModifier() {
    }

    public DishModifier(DishModifier dishModifier) {
        this(dishModifier.id, dishModifier.name, dishModifier.minValue, dishModifier.maxValue);
    }

    public DishModifier(Integer id, String name, Integer minValue, Integer maxValue) {
        super(id, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "DishModifier{" +
                "id=" + id +
                ", name=" + name +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
