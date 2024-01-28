package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.Analog;
import com.smetering.domain.entities.Discrete;
import com.smetering.domain.entities.Measurement;
import com.smetering.domain.entities.MeasurementValue;
import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class AllReadingsDisplay {
    private final GenericRepository<User, UUID> userRepository;


    public AllReadingsDisplay(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    public List<MeasurementValue> showAllValues(User user) {
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
                valuesToShow.addAll(((Analog) measurement).getAnalogValues());
            } else if (measurement instanceof Discrete) {
                valuesToShow.addAll(((Discrete) measurement).getDiscreteValues());
            }
        });

        return valuesToShow;
    }
}
