package ru.jegensomme.restaurant_service_system.model;

import org.springframework.util.CollectionUtils;

import org.hibernate.validator.constraints.Range;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends AbstractBaseEntity {

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime dateTime;
    @Column(name = "table_number", nullable = false)
    private int tableNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "discount")
    @Range(min = 0, max = 100)
    private int discount;

    private List<OrderDish> content;

    public Order() {
    }

    public Order(Integer id) {
        super(id);
    }

    public Order(Order order) {
        this(order.id, order.dateTime, order.tableNumber, order.status, order.discount, order.content);
    }

    public Order(Integer id, LocalDateTime dateTime, int tableNumber, OrderStatus status, int discount, Collection<OrderDish> content) {
        super(id);
        this.dateTime = dateTime;
        this.tableNumber = tableNumber;
        this.status = status;
        this.discount = discount;
        setContent(content);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
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

    public void setContent(Collection<OrderDish> content) {
        this.content = CollectionUtils.isEmpty(content) ? Collections.EMPTY_LIST : List.copyOf(content);
    }

    public List<OrderDish> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", tableNumber=" + tableNumber +
                ", status=" + status +
                ", orderContent=" + '\n' + content +
                '}';
    }
}
