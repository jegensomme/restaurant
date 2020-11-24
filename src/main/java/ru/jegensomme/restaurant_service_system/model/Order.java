package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Order.DELETE, query = "delete from Order o where o.id=:id")
})
@Entity
@Table(name = "orders", indexes = @Index(columnList = ("user_id, date_time"), name = "orders_user_id_date_time_idx"))
public class Order extends AbstractBaseEntity {

    public static final String DELETE = "Order.delete";

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "table_number", nullable = false)
    @NotNull
    private Integer tableNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar default 'PROCESSING'")
    private OrderStatus status;

    @Column(name = "discount", nullable = false, columnDefinition = "smallint default 0 check (discount >= 0 and discount <= 100)")
    @Range(min = 0, max = 100)
    private int discount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderDish> dishes;

    public Order() {
    }

    public Order(Order order) {
        this(order.id, order.user, order.dateTime, order.tableNumber, order.status, order.discount);
    }

    public Order(Integer id, LocalDateTime dateTime, Integer tableNumber, OrderStatus status, int discount) {
        this(id, null, dateTime, tableNumber, status, discount);
    }

    public Order(Integer id, User user, LocalDateTime dateTime, Integer tableNumber, OrderStatus status, int discount) {
        super(id);
        this.user = user;
        this.dateTime = dateTime;
        this.tableNumber = tableNumber;
        this.status = status;
        this.discount = discount;
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

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getTableNumber() {
        return tableNumber;
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
                ", tableNumber=" + tableNumber +
                ", status=" + status +
                '}';
    }
}
