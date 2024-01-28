package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.Analog;
import com.smetering.domain.entities.AnalogValue;
import com.smetering.domain.entities.Discrete;
import com.smetering.domain.entities.DiscreteValue;
import com.smetering.domain.entities.Measurement;
import com.smetering.domain.entities.MeasurementValue;
import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;


public class ActualReadingsDisplay {
    private final GenericRepository<User, UUID> userRepository;


    public ActualReadingsDisplay(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Отображение списка актуальных показаний счётчиков пользователя.
     *
     * @param user пользователь
     * @return список последних по времени значений для каждого счётчика, зарегистрированного для пользователя
     */
    public List<MeasurementValue> showActualValues(User user) {
        if (userRepository.get(user.getmRID()).isEmpty()) {
            return Collections.emptyList();
        }

        User currentUser = userRepository.get(user.getmRID()).get();
        currentUser.addActivityRecord("Пользователь " + user + " запросил актуальные показания счётчиков в " + LocalDateTime.now());
        userRepository.persist(currentUser);

        List<MeasurementValue> valuesToShow = new ArrayList<>();
        List<Measurement> userMeasurements = userRepository
                .get(user.getmRID())
                .get()
                .getCustomer()
                .getMeters()
                .stream().map(Meter::getMeasurement)
                .toList();

        userMeasurements.forEach(measurement -> {
            if (measurement instanceof Analog) {
                valuesToShow.add(((Analog) measurement).getAnalogValues()
                        .stream()
                        .max(Comparator.comparing(AnalogValue::getTimeStamp))
                        .orElse(null));
            } else if (measurement instanceof Discrete) {
                valuesToShow.add(((Discrete) measurement).getDiscreteValues()
                        .stream()
                        .max(Comparator.comparing(DiscreteValue::getTimeStamp))
                        .orElse(null));
            }
        });

        return valuesToShow;
    }

}
