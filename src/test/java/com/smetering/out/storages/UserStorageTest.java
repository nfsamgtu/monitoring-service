package com.smetering.out.storages;

import com.smetering.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserStorageTest {

    private UserStorage userStorage;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
    }

    @Test
    @DisplayName("Добавление и получение пользователя по UUID")
    void testPersistAndGetUser() {
        User user = new User("hashedPassword");
        user.setName("testUser");

        userStorage.persist(user);

        Optional<User> retrievedUser = userStorage.get(user.getmRID());
        assertThat(retrievedUser.isPresent()).isTrue();
        assertThat(retrievedUser.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("Получение всех пользователей")
    void testGetAllUsers() {
        User user1 = new User("hashedPassword1");
        user1.setName("user1");
        User user2 = new User("hashedPassword2");
        user2.setName("user2");

        userStorage.persist(user1);
        userStorage.persist(user2);

        Set<User> users = userStorage.get();
        assertThat(users).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    @DisplayName("Удаление пользователя")
    void testRemoveUser() {
        UUID userId = UUID.randomUUID();
        User user = new User("hashedPassword");
        user.setName("testUser");

        userStorage.persist(user);
        userStorage.remove(user);

        Optional<User> retrievedUser = userStorage.get(userId);
        assertThat(retrievedUser.isPresent()).isFalse();
    }
}
