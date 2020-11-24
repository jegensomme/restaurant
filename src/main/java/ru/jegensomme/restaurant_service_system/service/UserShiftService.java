package ru.jegensomme.restaurant_service_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.restaurant_service_system.model.Role;
import ru.jegensomme.restaurant_service_system.model.User;
import ru.jegensomme.restaurant_service_system.model.UserShift;
import ru.jegensomme.restaurant_service_system.repository.UserRepository;
import ru.jegensomme.restaurant_service_system.repository.UserShiftRepository;
import ru.jegensomme.restaurant_service_system.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserShiftService {

    private UserShiftRepository repository;
    private UserRepository userRepository;

    @Autowired
    public UserShiftService(UserShiftRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserShift create(UserShift userShift, int userId) {
        Assert.notNull(userShift, "userShift must not be null");
        return repository.save(userShift, userId);
    }

    @Transactional
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    @Transactional
    public void update(UserShift userShift, int userId) {
        Assert.notNull(userShift, "userShift must not be null");
        ValidationUtil.checkNotFoundWithId(repository.save(userShift, userId), userShift.id());
    }

    public UserShift get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public UserShift getByUserDate(int userId, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return ValidationUtil.checkNotFound(repository.getByUserDate(userId, date),
                "userId="+userId+", date="+date);
    }

    public List<UserShift> getAll() {
        return repository.getAll();
    }

    public List<UserShift> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }
}
