package ru.jegensomme.restaurant_service_system.model;

public class DishCategory extends AbstractNamedEntity {

    protected Integer groupId;

    public DishCategory() {
    }

    public DishCategory(Integer id) {
        super(id);
    }

    public DishCategory(Integer id, String name, Integer groupId) {
        super(id, name);
        this.groupId = groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }
}
