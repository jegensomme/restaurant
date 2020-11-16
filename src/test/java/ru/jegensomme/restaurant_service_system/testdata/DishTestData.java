package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.model.Dish;
import ru.jegensomme.restaurant_service_system.model.DishCategory;

public class DishTestData {

    public static final int NOT_FOUND = 7;

    public static final int DISH_GROUP1_ID = 1;
    public static final int DISH11_ID = 3;
    public static final int DISH12_ID = 4;

    public static final int DISH_GROUP2_ID = 2;
    public static final int DISH21_ID = 5;
    public static final int DISH22_ID = 6;

    public static final DishCategory DISH_GROUP1 = new Dish(DISH_GROUP1_ID, "Dish01",null, null);
    public static final Dish DISH11 = new Dish(DISH11_ID, "Dish11", DISH_GROUP1_ID, 2000);
    public static final Dish DISH12 = new Dish(DISH12_ID, "Dish12", DISH_GROUP1_ID, 3000);

    public static final DishCategory DISH_GROUP_2 = new Dish(DISH_GROUP2_ID, "Dish02", null, null);
    public static final Dish DISH21 = new Dish(DISH21_ID, "Dish21", DISH_GROUP2_ID, 2000);
    public static final Dish DISH22 = new Dish(DISH22_ID, "Dish22", DISH_GROUP2_ID, 3000);

    public static Dish getNew() {
        return new Dish(9, "New", null, 1000);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH11);
        updated.setCost(3000);
        return updated;
    }
}
