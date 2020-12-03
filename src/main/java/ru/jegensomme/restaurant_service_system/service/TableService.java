package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Table;
import ru.jegensomme.restaurant_service_system.repository.TableRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TableService {

    private final TableRepository repository;

    @Autowired
    public TableService(TableRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict(value = "tables")
    public Table create(Table table) throws CreateException {
        Assert.notNull(table, "table must not be null");
        return ValidationUtil.checkCreated(repository.save(table), Table.class);
    }

    @Transactional
    @CacheEvict(value = "tables")
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    @CacheEvict(value = "tables")
    public void update(Table table) throws NotFoundException {
        Assert.notNull(table, "table must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(table), table.id());
    }

    public Table get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable(value = "tables")
    public List<Table> getAll() {
        return repository.getAll();
    }
}
