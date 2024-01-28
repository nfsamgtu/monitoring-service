package com.smetering.domain.entities;

/**
 * Значение дискретного измерения
 *
 * Атрибуты класса:
 * value - значение измерения;
 * discrete - измеряемый дискретный параметр.
 */
public class DiscreteValue extends MeasurementValue {

    private final Long value;
    private final Discrete discrete;

    public DiscreteValue(Long value, Discrete discrete) {
        super();
        this.value = value;
        this.discrete = discrete;
    }

    public Long getValue() {
        return value;
    }

    public Discrete getDiscrete() {
        return discrete;
    }

}
