package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = OrderDish.DELETE, query = "delete from OrderDish od where od.id=:id")
})
@Entity
@Table(name = "order_dishes")
public class OrderDish extends AbstractBaseEntity {

    public static final String DELETE = "OrderDish.delete";

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Order order;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "amount", nullable = false, columnDefinition = "integer default 1 check ( amount > 0 )")
    @Range(min = 1)
    private int amount;

    @Column(name = "comment")
    private String comment;

    public OrderDish() {
    }

    public OrderDish(OrderDish orderDish) {
        this(orderDish.id, orderDish.order, orderDish.dish, orderDish.amount, orderDish.comment);
    }

    public OrderDish(Integer id, Order order, Dish dish, int amount, String comment) {
        super(id);
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
    public String toString() {
        return "OrderDIsh{" +
                "id=" + id +
                ", amount=" + amount +
                ", comment=" + comment +
                '}';
    }
}
