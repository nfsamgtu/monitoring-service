package com.smetering.domain.entities;

import com.smetering.domain.entities.enums.UnitMultiplier;
import com.smetering.domain.entities.enums.UnitSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Измеряемый дискретный параметр.
 *
 * Атрибуты класса:
 * maxValue - максимальное значение нормального диапазона допустимого значения для измерения;
 * minValue - множитель измерения;
 * normalValue - минимальное значение нормального диапазона допустимого значения для измерения;
 * discreteValues - значения дискретных измерений.
 */
public class Discrete extends Measurement {
    private final Long maxValue;
    private final Long minValue;
    private final Long normalValue;
    private final List<DiscreteValue> discreteValues;

    protected Discrete(UnitSymbol unitSymbol,
                       UnitMultiplier unitMultiplier,
                       String measurementType,
                       Long maxValue,
                       Long minValue,
                       Long normalValue) {
        super(unitSymbol, unitMultiplier, measurementType);
        this.discreteValues = new ArrayList<>();
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.normalValue = normalValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public Long getMinValue() {
        return minValue;
    }

    public Long getNormalValue() {
        return normalValue;
    }

    public List<DiscreteValue> getDiscreteValues() {
        return discreteValues;
    }

    public void addValue(DiscreteValue value) {
        this.discreteValues.add(value);
    }

}
