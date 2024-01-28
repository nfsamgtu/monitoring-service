package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.Analog;
import com.smetering.domain.entities.Discrete;
import com.smetering.domain.entities.Measurement;
import com.smetering.domain.entities.MeasurementValue;
import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;

import java.time.Month;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class SpecificReadingsDisplay {
    private final GenericRepository<User, UUID> userRepository;

    public SpecificReadingsDisplay(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Отображение показаний счётчиков пользователя за конкретный месяц
     *
     * @param user пользователь
     * @param month месяц, для которого нужно отобразить показания счётчиков
     * @param year год, для которого нужно отобразить показания счётчиков
     * @return список показаний счётчиков за конкретный месяц
     */
    public List<MeasurementValue> showSpecificValues(User user, Month month, Integer year) {
        if (userRepository.get(user.getmRID()).isEmpty()) {
            return Collections.emptyList();
        }

        User currentUser = userRepository.get(user.getmRID()).get();
        currentUser.addActivityRecord("Пользователь " + user + " запросил показания счётчиков за " + month + " " + year + " в " + LocalDateTime.now());
        userRepository.persist(currentUser);

        List<MeasurementValue> valuesToShow = new ArrayList<>();
        List<Measurement> userMeasurements = userRepository
                .get(user.getmRID())
                .get()
                .getCustomer()
                .getMeters()
                .stream()
                .map(Meter::getMeasurement)
                .toList();

        userMeasurements.forEach(measurement -> {
            if (measurement instanceof Analog) {
                valuesToShow.addAll(((Analog) measurement).getAnalogValues()
                        .stream()
                        .filter(value -> isSameMonth(value.getTimeStamp(), month, year))
                        .toList());
            } else if (measurement instanceof Discrete) {
                valuesToShow.addAll(((Discrete) measurement).getDiscreteValues()
                        .stream()
                        .filter(value -> isSameMonth(value.getTimeStamp(), month, year))
                        .toList());
            }
        });

        return valuesToShow;
    }

    private boolean isSameMonth(LocalDateTime timeStamp, Month month, Integer year) {
        return timeStamp.getMonth() == month && timeStamp.getYear() == year;
    }

}
