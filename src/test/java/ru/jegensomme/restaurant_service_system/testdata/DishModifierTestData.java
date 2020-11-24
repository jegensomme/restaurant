package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.DishModifier;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class DishModifierTestData {

    public static final TestMatcher<DishModifier> DISH_MODIFIER_MATCHER = TestMatcher.usingFieldsComparator();

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int DISH_MODIFIER1_ID = START_SEQ + 21;
    public static final int DISH_MODIFIER2_ID = DISH_MODIFIER1_ID + 1;


    public static final DishModifier DISH_MODIFIER1 = new DishModifier(DISH_MODIFIER1_ID, "DishModifier1", 1, 10);
    public static final DishModifier DISH_MODIFIER2 = new DishModifier(DISH_MODIFIER2_ID, "DishModifier2", 1, 10);

    public static DishModifier getNew() {
        return new DishModifier(null, "New", 1, null);
    }

    public static DishModifier getUpdated() {
        DishModifier updated = new DishModifier(DISH_MODIFIER1);
        updated.setName("Updated");
        return updated;
    }
}
