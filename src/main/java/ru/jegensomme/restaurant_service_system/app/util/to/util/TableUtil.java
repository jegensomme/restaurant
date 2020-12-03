package ru.jegensomme.restaurant_service_system.app.util.to.util;

import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.app.util.to.TableTo;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableUtil {

    private TableUtil() {
    }

    public static List<TableTo> getTos(Collection<Table> tables) {
        return filterByPredicate(tables, table -> true);
    }

    public static List<TableTo> filterByPredicate(Collection<Table> tables, Predicate<Table> filter) {
        return tables.stream()
                .filter(filter)
                .map(TableUtil::createTo)
                .collect(Collectors.toList());
    }

    public static TableTo createTo(Table table) {
        return new TableTo(table.getId(), table.getNumber(), table.getSeats());
    }
}
