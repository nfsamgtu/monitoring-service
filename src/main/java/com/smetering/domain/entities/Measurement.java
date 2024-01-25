package com.smetering.domain.entities;

import com.smetering.domain.entities.enums.UnitMultiplier;
import com.smetering.domain.entities.enums.UnitSymbol;

/**
 * Измеряемый параметр.
 *
 * Атрибуты класса:
 * unitSymbol - единица измерения;
 * unitMultiplier - множитель измерения;
 * measurementType - тип измерения.
 */
public abstract class Measurement extends IdentifiedObject {

    private final UnitSymbol unitSymbol;
    private final UnitMultiplier unitMultiplier;
    private final String measurementType;

    protected Measurement(UnitSymbol unitSymbol, UnitMultiplier unitMultiplier, String measurementType) {
        super();
        this.unitSymbol = unitSymbol;
        this.unitMultiplier = unitMultiplier;
        this.measurementType = measurementType;
    }


    public UnitSymbol getUnitSymbol() {
        return unitSymbol;
    }

    public UnitMultiplier getUnitMultiplier() {
        return unitMultiplier;
    }

    public String getMeasurementType() {
        return measurementType;
    }

}
