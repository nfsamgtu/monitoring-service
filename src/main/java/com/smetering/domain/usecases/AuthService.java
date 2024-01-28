package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private final GenericRepository<User, UUID> repository;

    public AuthService(GenericRepository<User, UUID> repository) {
        this.repository = repository;
    }


    /**
     * Авторизация пользователя.
     *
     * @param username имя пользователя.
     * @param password пароль.
     * @return опциональный игрок.
     */
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = repository.get().stream().filter(u -> u.getName().equals(username)).findFirst();
        Optional<User> result = userOpt.filter(u -> BCrypt.checkpw(password, u.getHashedPassword()));
        if (result.isPresent()) {
            result.get().addActivityRecord("Пользователь " + result.get().getName() + " авторизован" + LocalDateTime.now());
        } else {
            userOpt.ifPresent(user -> user.addActivityRecord("Неуспешная попытка авторизации пользователя " + user.getName() + " " + LocalDateTime.now()));
        }

        return result;
    }

    /**
     * Логаут пользователя
     *
     * @param user пользователь
     */
    public void logout(User user) {
        if (repository.get(user.getmRID()).isEmpty()) {
            return;
        }

        User currentUser = repository.get(user.getmRID()).get();
        currentUser.addActivityRecord("Пользователь " + user + " вышел из учётной записи в " + LocalDateTime.now());
        repository.persist(currentUser);
    }
}
