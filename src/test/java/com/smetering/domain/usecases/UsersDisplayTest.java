package com.smetering.domain.usecases;

import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UsersDisplayTest {

    private GenericRepository<User, UUID> userRepository;
    private UsersDisplay usersDisplay;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(GenericRepository.class);
        usersDisplay = new UsersDisplay(userRepository);
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    void testGetAllUsers() {
        User user1 = new User("hashedPassword1");
        user1.setName("User1");
        User user2 = new User("hashedPassword2");
        user2.setName("User2");

        when(userRepository.get()).thenReturn(Set.of(user1, user2));

        List<User> users = usersDisplay.get();

        assertThat(users).hasSize(2);
        assertThat(users).containsExactlyInAnyOrder(user1, user2);
    }

}
