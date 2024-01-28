package com.smetering.domain.usecases;

import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ActivityRecordsDisplay {
    private final GenericRepository<User, UUID> userRepository;


    public ActivityRecordsDisplay(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Показать историю активности всех пользователей системы
     *
     * @return список записей с историей активности всех пользователей системы
     */
    public List<String> get() {
        List<String> result = new ArrayList<>();
        userRepository.get().forEach(user -> result.addAll(user.getActivityHistory()));
        return result;
    }

    /**
     * Показать историю активности выбранного пользователя
     *
     * @param user пользователь
     * @return список записей с историей активности выбранного пользователя
     */
    public List<String> get(User user) {
        if (userRepository.get(user.getmRID()).isEmpty()) {
            return Collections.emptyList();
        }

        return userRepository.get(user.getmRID()).get().getActivityHistory();
    }

}
