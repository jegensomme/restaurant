package ru.jegensomme.restaurant_service_system.testdata;

import ru.jegensomme.restaurant_service_system.TestMatcher;
import ru.jegensomme.restaurant_service_system.model.Table;

import static ru.jegensomme.restaurant_service_system.model.AbstractBaseEntity.START_SEQ;

public class TableTestData {

    public static final TestMatcher<Table> TABLE_MATCHER = TestMatcher.usingFieldsComparator();

    public static final int NOT_FOUND = START_SEQ + 100;

    public static final int TABLE1_ID = START_SEQ + 9;
    public static final int TABLE2_ID = TABLE1_ID + 1;
    public static final int TABLE3_ID = TABLE1_ID + 2;
    public static final int TABLE4_ID = TABLE1_ID + 3;

    public static final Table TABLE1 = new Table(TABLE1_ID, 1, 1);
    public static final Table TABLE2 = new Table(TABLE2_ID, 2, 2);
    public static final Table TABLE3 = new Table(TABLE3_ID, 3, 3);
    public static final Table TABLE4 = new Table(TABLE4_ID, 4, 4);

    public static Table getNew() {
        return new Table(null, 1, 1);
    }

    public static Table getUpdated() {
        Table updated = new Table(TABLE1);
        updated.setSeats(4);
        return updated;
    }
}