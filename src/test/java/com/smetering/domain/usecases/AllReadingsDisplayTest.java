package com.smetering.domain.usecases;

import com.smetering.domain.entities.*;
import com.smetering.out.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AllReadingsDisplayTest {

    private GenericRepository<User, UUID> userRepository;
    private AllReadingsDisplay allReadingsDisplay;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(GenericRepository.class);
        allReadingsDisplay = new AllReadingsDisplay(userRepository);
    }

    @Test
    @DisplayName("Проверка всех показаний счётчиков пользователя")
    void testShowAllValues() {
        User user = new User("hashedPassword");
        Meter meter1 = createMeterWithAnalogValues();
        Meter meter2 = createMeterWithDiscreteValues();
        user.getCustomer().addMeter(meter1);
        user.getCustomer().addMeter(meter2);

        when(userRepository.get(user.getmRID())).thenReturn(Optional.of(user));

        List<MeasurementValue> allValues = allReadingsDisplay.showAllValues(user);

        assertThat(allValues).hasSizeGreaterThanOrEqualTo(2);
    }

    private Meter createMeterWithAnalogValues() {
        Meter meter = new Meter(60.0, "123", "Вт*ч", "к", "ручной");
        Analog analog = new Analog("Вт*ч", "к", "ручной", true, 1000.0, 0.0, 1.0);
        analog.addValue(new AnalogValue(10.5, analog));
        analog.addValue(new AnalogValue(11.0, analog));
        meter.setMeasurement(analog);
        return meter;
    }

    private Meter createMeterWithDiscreteValues() {
        Meter meter = new Meter(60.0, "456", "Вт*ч", "к", "ручной");
        Discrete discrete = new Discrete("Вт*ч", "к", "ручной", 1000L, 0L, 1L);
        discrete.addValue(new DiscreteValue(100L, discrete));
        discrete.addValue(new DiscreteValue(200L, discrete));
        meter.setMeasurement(discrete);
        return meter;
    }
}
