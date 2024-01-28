package com.smetering.domain.usecases;

import com.smetering.domain.entities.User;
import com.smetering.domain.entities.enums.Role;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterServiceTest {

    private GenericRepository<User, UUID> repository;
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GenericRepository.class);
        registerService = new RegisterService(repository);
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    void testSuccessfulRegistration() {
        String username = "newUser";
        String password = "password";

        when(repository.get()).thenReturn(Collections.emptySet());

        Optional<User> registeredUser = registerService.register(username, password);

        assertThat(registeredUser.isPresent()).isTrue();
        assertThat(registeredUser.get().getName()).isEqualTo(username);
        verify(repository, times(1)).persist(any(User.class));
    }

    @Test
    @DisplayName("Попытка регистрации пользователя с существующим именем")
    void testRegistrationWithExistingUsername() {
        String username = "existingUser";
        String password = "password";
        User existingUser = new User(BCrypt.hashpw(password, BCrypt.gensalt()));
        existingUser.setName(username);

        when(repository.get()).thenReturn(Set.of(existingUser));

        Optional<User> registeredUser = registerService.register(username, password);

        assertThat(registeredUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Назначение роли администратора для пользователя с именем 'admin'")
    void testAdminRoleAssignment() {
        String username = "admin";
        String password = "adminPass";

        when(repository.get()).thenReturn(Collections.emptySet());

        Optional<User> registeredUser = registerService.register(username, password);

        assertThat(registeredUser.isPresent()).isTrue();
        assertThat(registeredUser.get().getRole()).isEqualTo(Role.ROLE_ADMIN);
    }
}
