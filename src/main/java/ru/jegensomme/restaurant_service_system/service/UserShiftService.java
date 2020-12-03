package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.repository.UserShiftRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;
import ru.jegensomme.restaurant_service_system.util.exception.CreateException;
import ru.jegensomme.restaurant_service_system.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserShiftService {

    private final UserShiftRepository repository;

    @Autowired
    public UserShiftService(UserShiftRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserShift create(UserShift userShift, int userId) throws CreateException {
        Assert.notNull(userShift, "userShift must not be null");
        return ValidationUtil.checkCreated(repository.save(userShift, userId), UserShift.class);
    }

    @Transactional
    public void delete(int id) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(UserShift userShift, int userId) throws NotFoundException {
        Assert.notNull(userShift, "userShift must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(userShift, userId), userShift.id());
    }

    public UserShift get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<UserShift> getAll() {
        return repository.getAll();
    }

    public List<UserShift> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    public List<UserShift> getOpenedByUser(int userId) {
        return repository.getOpenedByUser(userId);
    }

    public List<UserShift> getBetweenInclusiveByUser(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getBetweenInclusiveByUser(userId, startDate, endDate);
    }

    public Float getTotalSalesByUserShift(int id) {
        return repository.getTotalSalesByUserShift(id);
    }
}
