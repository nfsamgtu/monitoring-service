package com.smetering.domain.usecases;

import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;

import java.util.List;
import java.util.UUID;

public class UsersDisplay {
    private final GenericRepository<User, UUID> userRepository;

    public UsersDisplay(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Показать всех пользователей
     * @return список всех пользователей
     */
    public List<User> get() {
        return userRepository.get().stream().toList();
    }
}
