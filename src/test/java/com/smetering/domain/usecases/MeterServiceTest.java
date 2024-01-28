package com.smetering.domain.usecases;

import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MeterServiceTest {

    private GenericRepository<User, UUID> repository;
    private MeterService meterService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GenericRepository.class);
        meterService = new MeterService(repository);
    }

    @Test
    @DisplayName("Успешное добавление прибора учёта пользователю")
    void testRegisterMeterSuccessfully() {
        User user = new User("hashedPassword");
        user.setName("testUser");

        when(repository.get(user.getmRID())).thenReturn(Optional.of(user));

        Double timeZoneOffset = 60.0;
        String serialNumber = "12345";
        String unitSymbol = "Вт";
        String unitMultiplier = "кВт";
        String measurementType = "Электричество";

        Optional<Meter> registeredMeter = meterService.registerMeter(user, timeZoneOffset, serialNumber, unitSymbol, unitMultiplier, measurementType);

        assertThat(registeredMeter.isPresent()).isTrue();
        assertThat(registeredMeter.get().getSerialNumber()).isEqualTo(serialNumber);
        verify(repository, times(1)).persist(user);
    }

    @Test
    @DisplayName("Неудачное добавление прибора учёта из-за отсутствия пользователя")
    void testRegisterMeterUnsuccessfully() {
        User user = new User("hashedPassword");
        user.setName("testUser");

        when(repository.get(user.getmRID())).thenReturn(Optional.empty());

        Double timeZoneOffset = 60.0;
        String serialNumber = "12345";
        String unitSymbol = "Вт";
        String unitMultiplier = "кВт";
        String measurementType = "Электричество";

        Optional<Meter> registeredMeter = meterService.registerMeter(user, timeZoneOffset, serialNumber, unitSymbol, unitMultiplier, measurementType);

        assertThat(registeredMeter.isPresent()).isFalse();
    }
}
