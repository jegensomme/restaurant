package ru.jegensomme.restaurant_service_system.model;

public class DishModifier extends AbstractNamedEntity {

    private int minValue;

    private int maxValue;

    private Dish dish;

    public DishModifier() {
    }

    public DishModifier(Integer id, String name, int minValue, int maxValue) {
        super(id, name);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Dish getDish() {
        return dish;
    }

    @Override
    public String toString() {
        return "DishModifier{" +
                "id=" + id +
                ", name=" + name +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
