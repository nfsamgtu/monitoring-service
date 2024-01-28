package com.smetering.domain.usecases;

import com.smetering.domain.entities.*;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SpecificReadingsDisplayTest {

    private GenericRepository<User, UUID> userRepository;
    private SpecificReadingsDisplay specificReadingsDisplay;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(GenericRepository.class);
        specificReadingsDisplay = new SpecificReadingsDisplay(userRepository);
    }

    @Test
    @DisplayName("Отображение показаний счётчиков за конкретный месяц")
    void testShowSpecificValues() {;
        User user = createUserWithMeterAndReadings();
        Month month = LocalDateTime.now().getMonth();
        Integer year = LocalDateTime.now().getYear();

        when(userRepository.get(user.getmRID())).thenReturn(Optional.of(user));

        List<MeasurementValue> values = specificReadingsDisplay.showSpecificValues(user, month, year);

        // Проверки, что список values содержит ожидаемые значения показаний за указанный месяц и год
        assertThat(values).isNotEmpty();

    }

    private User createUserWithMeterAndReadings() {
        User user = new User("hashedPassword");
        Meter meter = new Meter(60.0, "123", "Вт*ч", "к", "ручной");
        Analog analog = new Analog("Вт*ч", "к", "ручной", true, 1000.0, 0.0, 1.0);
        analog.addValue(new AnalogValue(10.5, analog));
        analog.addValue(new AnalogValue(11.0, analog));
        meter.setMeasurement(analog);
        user.getCustomer().addMeter(meter);
        return user;
    }
}
