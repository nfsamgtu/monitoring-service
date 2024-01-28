package com.smetering.domain.entities;

import com.smetering.domain.entities.enums.UnitMultiplier;
import com.smetering.domain.entities.enums.UnitSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Измеряемый аналоговый параметр.
 *
 * Атрибуты класса:
 * positiveFlowIn - указывает, что положительное значение перетока ресурса соответствует направлению извне к потребителю;
 * maxValue - максимальное значение нормального диапазона допустимого значения для измерения;
 * minValue - множитель измерения;
 * normalValue - минимальное значение нормального диапазона допустимого значения для измерения;
 * analogValues - список значений, относящихся к данному измеряемому аналоговому параметру.
 */
public class Analog extends Measurement {
    private final Boolean positiveFlowIn;
    private final Double maxValue;
    private final Double minValue;
    private final Double normalValue;
    private final List<AnalogValue> analogValues;


    public Analog(String unitSymbol,
                     String unitMultiplier,
                     String measurementType,
                     Boolean positiveFlowIn,
                     Double maxValue,
                     Double minValue,
                     Double normalValue) {
        super(unitSymbol, unitMultiplier, measurementType);
        this.analogValues = new ArrayList<>();
        this.positiveFlowIn = positiveFlowIn;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.normalValue = normalValue;
    }

    public Boolean getPositiveFlowIn() {
        return positiveFlowIn;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getNormalValue() {
        return normalValue;
    }

    public List<AnalogValue> getAnalogValues() {
        return analogValues;
    }

    public void addValue(AnalogValue value) {
        this.analogValues.add(value);
    }

}
