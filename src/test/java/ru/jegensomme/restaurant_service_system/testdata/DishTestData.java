package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Dish;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.DISH_CATEGORY1;
import static ru.jegensomme.restaurant_service_system.testdata.DishCategoryTestData.DISH_CATEGORY2;
import static ru.jegensomme.restaurant_service_system.testdata.DishModifierTestData.DISH_MODIFIER1;
import static ru.jegensomme.restaurant_service_system.testdata.DishModifierTestData.DISH_MODIFIER2;

public class DishTestData {

    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsComparator("modifiers", "entry");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int DISH1_ID = START_SEQ + 5;
    public static final int DISH2_ID = DISH1_ID + 1;
    public static final int DISH3_ID = DISH1_ID + 2;
    public static final int DISH4_ID = DISH1_ID + 3;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Dish1", DISH_CATEGORY1, 2000, DISH_MODIFIER1);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Dish2", DISH_CATEGORY1, 3000, DISH_MODIFIER2);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Dish3", DISH_CATEGORY2, 2000, DISH_MODIFIER1);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Dish4", DISH_CATEGORY2, 3000, DISH_MODIFIER2);

    public static Dish getNew() {
        return new Dish(null, "New", DISH_CATEGORY1, 1000, DISH_MODIFIER1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH1);
        updated.setName("Updated");
        return updated;
    }
}
