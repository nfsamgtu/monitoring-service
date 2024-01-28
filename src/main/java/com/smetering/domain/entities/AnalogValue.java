package com.smetering.domain.entities;

/**
 * Значение аналогового измерения
 *
 * Атрибуты класса:
 * value - значение измерения;
 * analog - измеряемый аналоговый параметр.
 */
public class AnalogValue extends MeasurementValue {

    private final Double value;
    private final Analog analog;

    public AnalogValue(Double value, Analog analog) {
        super();
        this.value = value;
        this.analog = analog;
    }

    public Double getValue() {
        return value;
    }

    public Analog getAnalog() {
        return analog;
    }

}
