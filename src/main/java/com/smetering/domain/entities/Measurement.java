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

    private final String unitSymbol;
    private final String unitMultiplier;
    private final String measurementType;

    protected Measurement(String unitSymbol, String unitMultiplier, String measurementType) {
        super();
        this.unitSymbol = unitSymbol;
        this.unitMultiplier = unitMultiplier;
        this.measurementType = measurementType;
    }


    public String getUnitSymbol() {
        return unitSymbol;
    }

    public String getUnitMultiplier() {
        return unitMultiplier;
    }

    public String getMeasurementType() {
        return measurementType;
    }

}
