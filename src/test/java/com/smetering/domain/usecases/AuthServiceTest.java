package com.smetering.domain.usecases;

import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    private GenericRepository<User, UUID> repository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GenericRepository.class);
        authService = new AuthService(repository);
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    void testSuccessfulLogin() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setName(username);

        when(repository.get()).thenReturn(Set.of(user));

        Optional<User> result = authService.login(username, password);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(username);
    }

    @Test
    @DisplayName("Неуспешная авторизация пользователя")
    void testUnsuccessfulLogin() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setName(username);

        when(repository.get()).thenReturn(Set.of(user));

        Optional<User> result = authService.login(username, "wrongPassword");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Выход пользователя из системы")
    void testLogout() {
        User user = new User("hashedPassword");

        when(repository.get(user.getmRID())).thenReturn(Optional.of(user));

        authService.logout(user);

        verify(repository, times(1)).persist(user);
    }

}