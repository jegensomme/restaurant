package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.hibernate.annotations.Cache;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = DishCategory.DELETE, query = "delete from  DishCategory dc where dc.id=:id")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dish_categories")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DishCategory extends AbstractNamedEntity {

    public static final String DELETE = "DishCategory.delete";

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected DishCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<DishCategory> entry;

    public DishCategory() {
    }

    public DishCategory(DishCategory dishCategory) {
        this(dishCategory.id, dishCategory.name, dishCategory.category);
    }

    public DishCategory(Integer id, String name, DishCategory category) {
        super(id, name);
        this.category = category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public DishCategory getCategory() {
        return category;
    }

    public List<DishCategory> getEntry() {
        return entry;
    }

    public void setEntry(List<DishCategory> entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "DishCategory{" +
                "id=" + id +
                ", name=" + name +
                ", category=" + category +
                '}';
    }
}
