package com.smetering.domain.usecases;

import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ReadingSubmitterServiceTest {

    private GenericRepository<User, UUID> userRepository;
    private ReadingSubmitterService readingSubmitterService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(GenericRepository.class);
        readingSubmitterService = new ReadingSubmitterService(userRepository);
    }

    @Test
    @DisplayName("Успешное добавление показаний к счётчику")
    void testSubmitMeasurementValueSuccessfully() {
        User user = createUserWithMeter();
        Meter meter = user.getCustomer().getMeters().getFirst();

        when(userRepository.get(user.getmRID())).thenReturn(Optional.of(user));

        readingSubmitterService.submitMeasurementValue(user, meter, "100");

        verify(userRepository, times(1)).persist(user);
    }

    @Test
    @DisplayName("Попытка добавить показания для несуществующего пользователя")
    void testSubmitMeasurementValueNonExistingUser() {
        User user = new User("hashedPassword");
        Meter meter = new Meter(60.0, "123", "Вт*ч", "к", "ручной");

        when(userRepository.get(user.getmRID())).thenReturn(Optional.empty());

        readingSubmitterService.submitMeasurementValue(user, meter, "100");

        verify(userRepository, never()).persist(any());
    }

    private User createUserWithMeter() {
        User user = new User("hashedPassword");
        Meter meter = new Meter(60.0, "123", "Вт*ч", "к", "ручной");
        user.getCustomer().addMeter(meter);

        return user;
    }
}
