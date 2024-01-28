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
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ActivityRecordsDisplayTest {

    private GenericRepository<User, UUID> userRepository;
    private ActivityRecordsDisplay activityRecordsDisplay;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(GenericRepository.class);
        activityRecordsDisplay = new ActivityRecordsDisplay(userRepository);
    }

    @Test
    @DisplayName("Проверка истории активности всех пользователей")
    void testGetAllUsersActivityHistory() {
        User user1 = new User("hashedPassword1");
        user1.addActivityRecord("User1 activity 1");
        user1.addActivityRecord("User1 activity 2");

        User user2 = new User("hashedPassword2");
        user2.addActivityRecord("User2 activity 1");

        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);

        when(userRepository.get()).thenReturn(userSet);

        List<String> expectedActivityHistory = Arrays.asList("User1 activity 1", "User1 activity 2", "User2 activity 1");
        List<String> actualActivityHistory = activityRecordsDisplay.get();

        assertThat(actualActivityHistory).containsExactlyElementsOf(expectedActivityHistory);
    }

    @Test
    @DisplayName("Проверка истории активности конкретного пользователя")
    void testGetSpecificUserActivityHistory() {
        User user = new User("hashedPassword");
        user.addActivityRecord("User activity 1");
        user.addActivityRecord("User activity 2");

        when(userRepository.get(user.getmRID())).thenReturn(Optional.of(user));

        List<String> expectedActivityHistory = Arrays.asList("User activity 1", "User activity 2");
        List<String> actualActivityHistory = activityRecordsDisplay.get(user);

        assertThat(actualActivityHistory).containsExactlyElementsOf(expectedActivityHistory);
    }
}