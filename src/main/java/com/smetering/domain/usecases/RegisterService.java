package com.smetering.domain.usecases;

import com.smetering.data.repositories.GenericRepository;
import com.smetering.domain.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public final class RegisterService {

    private final GenericRepository<User, UUID> repository;

    public RegisterService(GenericRepository<User, UUID> repository) {
        this.repository = repository;
    }

    /**
     * Регистрация нового пользователя системы
     *
     * @param name имя пользователя
     * @param password пароль
     * @return зарегистрированный пользователь
     */
    public Optional<User> register(String name, String password) {
        if (repository.get().stream().anyMatch(u -> u.getName().equals(name))) {
            return Optional.empty();
        };
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(hashedPassword);
        newUser.setName(name);
        newUser.addActivityRecord("Пользователь " + name + " зарегистрирован в " + LocalDateTime.now());
        repository.persist(newUser);

        return Optional.of(newUser);
    }

}
