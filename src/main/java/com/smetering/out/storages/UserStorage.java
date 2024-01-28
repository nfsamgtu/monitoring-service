package com.smetering.out.storages;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Класс, обеспечивающий хранение информационной модели пользователей в оперативной памяти.
 */
public class UserStorage implements GenericRepository<User, UUID> {
    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public Optional<User> get(UUID mRID) {
        return Optional.ofNullable(users.get(mRID));
    }

    @Override
    public Set<User> get() {
        return new HashSet<>(users.values());
    }

    @Override
    public void persist(User entity) {
        users.put(entity.getmRID(), entity);
    }

    @Override
    public void remove(User entity) {
        users.remove(entity.getmRID(), entity);
    }

}
