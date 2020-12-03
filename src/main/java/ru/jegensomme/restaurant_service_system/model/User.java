package ru.jegensomme.restaurant_service_system.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import org.hibernate.annotations.Cache;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "delete from User u where u.id=:id")
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "key", name = "user_unique_key_idx")})
public class User extends AbstractNamedEntity {

    public static final String DELETE = "User.delete";

    @Column(name = "key", nullable = false, unique = true)
    @NotBlank
    @Size(min = 4, max = 10)
    private String key;

    @Enumerated(EnumType.STRING)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserShift> shifts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

    public User() {
    }

    public User(User user) {
        this(user.id, user.name, user.key, user.roles);
    }

    public User(Integer id, String name, String key, Role role, Role... roles) {
        this(id, name, key, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String key, Collection<Role> roles) {
        super(id, name);
        this.key = key;
        setRoles(roles);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<UserShift> getShifts() {
        return shifts;
    }

    public void setShifts(List<UserShift> shifts) {
        this.shifts = shifts;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", key=" + key +
                ", roles=" + roles +
                '}';
    }
}
