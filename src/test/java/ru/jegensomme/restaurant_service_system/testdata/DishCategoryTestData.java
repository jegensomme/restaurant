package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.DishCategory;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class DishCategoryTestData {

    public static final TestMatcher<DishCategory> DISH_CATEGORY_MATCHER = TestMatcher.usingFieldsComparator("entry");

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int DISH_CATEGORY1_ID = START_SEQ + 3;
    public static final int DISH_CATEGORY2_ID = DISH_CATEGORY1_ID + 1;

    public static final DishCategory DISH_CATEGORY1 = new DishCategory(DISH_CATEGORY1_ID, "DishCategory1", null);
    public static final DishCategory DISH_CATEGORY2 = new DishCategory(DISH_CATEGORY2_ID, "DishCategory2", null);

    public static DishCategory getNew() {
        return new DishCategory(null, "New", null);
    }

    public static DishCategory getUpdated() {
        DishCategory updated = new DishCategory(DISH_CATEGORY1);
        updated.setName("Updated");
        return updated;
    }

}

