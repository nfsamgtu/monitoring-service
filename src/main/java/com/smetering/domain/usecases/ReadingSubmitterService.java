package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReadingSubmitterService {
    private final GenericRepository<User, UUID> userRepository;


    public ReadingSubmitterService(GenericRepository<User, UUID> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Добавление показаний к счётчику.
     *
     * @param user пользователь
     * @param meter счётчик, для которого добавляются показания
     * @param value значение показаний
     */
    public void submitMeasurementValue(User user, Meter meter, String value) {
        if (userRepository.get(user.getmRID()).isEmpty()) {
            return;
        }

        User currentUser = userRepository.get(user.getmRID()).get();

        Measurement measurement = currentUser
                .getCustomer()
                .getMeters()
                .stream()
                .filter(m -> m.getmRID().equals(meter.getmRID()))
                .findFirst()
                .get()
                .getMeasurement();

        LocalDateTime now = LocalDateTime.now();
        boolean alreadyExists = false;

        if (measurement instanceof Analog) {
            alreadyExists = ((Analog) measurement).getAnalogValues().stream()
                    .anyMatch(val -> val.getTimeStamp().getMonth() == now.getMonth() && val.getTimeStamp().getYear() == now.getYear());
            if (!alreadyExists) {
                ((Analog) measurement).getAnalogValues().add(new AnalogValue(Double.valueOf(value), (Analog) measurement));
            } else {
                currentUser.addActivityRecord("Пользователь " + user + " попытался добавить существующие показания счётчика №" + meter.getSerialNumber() + " в " + now);
                userRepository.persist(currentUser);
                return;
            }
        } else if (measurement instanceof Discrete) {
            alreadyExists = ((Discrete) measurement).getDiscreteValues().stream()
                    .anyMatch(val -> val.getTimeStamp().getMonth() == now.getMonth() && val.getTimeStamp().getYear() == now.getYear());
            if (!alreadyExists) {
                ((Discrete) measurement).getDiscreteValues().add(new DiscreteValue(Long.valueOf(value), (Discrete) measurement));
            } else {
                currentUser.addActivityRecord("Пользователь " + user + " попытался добавить существующие показания счётчика №" + meter.getSerialNumber() + " в " + now);
                userRepository.persist(currentUser);
                return;
            }
        }

        if (!alreadyExists) {
            currentUser.addActivityRecord("Пользователь " + user + " успешно добавил показания счётчика №" + meter.getSerialNumber() + " в " + now);
            userRepository.persist(currentUser);
        }
    }

}
