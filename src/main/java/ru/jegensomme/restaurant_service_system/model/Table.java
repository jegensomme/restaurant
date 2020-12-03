package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Table.DELETE, query = "delete from Table t where t.id=:id")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@javax.persistence.Table(name = "tables", uniqueConstraints = {@UniqueConstraint(columnNames = "number", name = "tables_unique_idx")})
public class Table extends AbstractBaseEntity {

    public static final String DELETE = "OrderTable.Delete";

    @Column(name = "number", nullable = false)
    @NotNull
    private Integer number;

    @Column(name = "seats", nullable = false, columnDefinition = "smallint default 1")
    @Range(min = 1)
    private int seats;

    public Table() {
    }

    public Table(Table table) {
        this(table.id, table.number, table.seats);
    }

    public Table(Integer id, Integer number, int seats) {
        super(id);
        this.number = number;
        this.seats = seats;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", number=" + number +
                ", seats=" + seats +
                '}';
    }
}
