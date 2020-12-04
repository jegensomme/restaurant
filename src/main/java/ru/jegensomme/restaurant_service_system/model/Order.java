package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Order.DELETE, query = "delete from Order o where o.id=:id")
})
@Entity
@javax.persistence.Table(name = "orders", indexes = @Index(columnList = ("user_id, date_time"), name = "orders_user_id_date_time_idx"))
public class Order extends AbstractBaseEntity {

    public static final String DELETE = "Order.delete";

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "table_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Table table;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar default 'PROCESSING'")
    private OrderStatus status;

    @Column(name = "discount", nullable = false)
    @NotNull
    @Range(min = 0, max = 100)
    private Integer discount = 0;

    @Column(name = "check_amount", nullable = false)
    @NotNull
    @Range(min = 0)
    private double checkAmount = 0;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDish> dishes = Collections.EMPTY_LIST;

    public Order() {
    }

    public Order(Order order) {
        this(order.id, order.dateTime, order.table, order.status);
    }

    public Order(Integer id, LocalDateTime dateTime, Table table, OrderStatus status) {
        super(id);
        this.dateTime = dateTime;
        this.table = table;
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public double getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(double cost) {
        this.checkAmount = cost;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", table=" + table +
                ", discount=" + discount +
                ", cost=" + checkAmount +
                ", status=" + status +
                '}';
    }
}
