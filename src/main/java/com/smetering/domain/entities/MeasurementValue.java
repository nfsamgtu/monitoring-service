package com.smetering.domain.entities;

import java.time.LocalDateTime;

/**
 * Значение измерения.
 *
 * Атрибуты класса:
 * timeStamp - время измерения;
 */
public abstract class MeasurementValue extends IdentifiedObject {
    private final LocalDateTime timeStamp;

    protected MeasurementValue() {
        super();
        this.timeStamp = LocalDateTime.now();
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
