package com.smetering.domain.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Потребитель энергетических ресурсов.
 *
 * Атрибуты класса:
 * meters - список приборов учёта, закреплённых за потребителем.
 */
public class Customer extends IdentifiedObject {

    private final List<Meter> meters;

    public Customer() {
        super();
        this.meters = new ArrayList<>();
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public void addMeter(Meter meter) {
        this.meters.add(meter);
    }

}
