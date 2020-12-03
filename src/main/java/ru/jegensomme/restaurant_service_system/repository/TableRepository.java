package ru.jegensomme.restaurant_service_system.repository;

import ru.jegensomme.restaurant_service_system.model.Table;

import java.util.List;

public interface TableRepository {

    Table save(Table table);

    boolean delete(int id);

    Table get(int id);

    List<Table> getAll();

}
