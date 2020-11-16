package ru.jegensomme.restaurant_service_system.to;

import ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity;
import ru.jegensomme.restaurant_service_system.model.Dish;

public class DishTo {

    private final Integer dishId;

    private final int amount;

    private final String comment;

    public DishTo(Integer dishId, int amount, String comment) {
        this.dishId = dishId;
        this.amount = amount;
        this.comment = comment;
    }

    public Integer getDishId() {
        return dishId;
    }

    public int getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        DishTo that = (DishTo) o;
        return dishId != null &&
                dishId.equals(that.dishId) &&
                amount == that.amount &&
                (comment.equals(that.comment) || (comment == null && that.comment == null));
    }

    @Override
    public int hashCode() {
        return dishId == null ? 0 : dishId;
    }
}
