package com.smetering.domain.usecases;

import com.smetering.out.repositories.GenericRepository;
import com.smetering.domain.entities.Meter;
import com.smetering.domain.entities.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class MeterService {
    private final GenericRepository<User, UUID> repository;

    public MeterService(GenericRepository<User, UUID> repository) {
        this.repository = repository;
    }


    /**
     * Добавить прибор учёта пользователю
     *
     * @param user пользователь
     * @param timeZoneOffset смещение часового пояса относительно GMT для местоположения этого устройства в минутах
     * @param serialNumber серийный номер устройства
     * @param unitSymbol единица измерения
     * @param unitMultiplier множитель измерения
     * @param measurementType тип измерения
     * @return добавленный прибор учёта
     */
    public Optional<Meter> registerMeter(User user,
                                         Double timeZoneOffset,
                                         String serialNumber,
                                         String unitSymbol,
                                         String unitMultiplier,
                                         String measurementType) {
        Meter meter = new Meter(timeZoneOffset, serialNumber, unitSymbol, unitMultiplier, measurementType);

        if (repository.get(user.getmRID()).isEmpty()) {
            return Optional.empty();
        }

        User currentUser = repository.get(user.getmRID()).get();


        currentUser.getCustomer().addMeter(meter);
        currentUser.addActivityRecord("Пользователь " + user.getName() + " добавил прибор учёта №" + serialNumber + " в " + LocalDateTime.now());
        this.repository.persist(currentUser);

        return Optional.of(meter);
    }

}
