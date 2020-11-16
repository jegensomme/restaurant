package ru.jegensomme.restaurant_service_system.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "key", name = "user_unique_key_idx")})
public class User extends AbstractNamedEntity {

    @Column(name = "key", nullable = false, unique = true)
    @NotBlank
    @Size(min = 4, max = 10)
    private String key;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(User user) {
        this(user.id, user.name, user.key, user.roles);
    }

    public User(Integer id, String name, String key, Role role, Role... roles) {
        super(id, name);
        this.key = key;
        this.roles = EnumSet.of(role, roles);
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

    @Override
    public String toString() {
        return "DishModifier{" +
                "id=" + id +
                ", name=" + name +
                ", key=" + key +
                ", roles=" + roles +
                '}';
    }
}
