package com.smetering.domain.entities;


/**
 * Устройство учета, которое выполняет измерительную функцию в точке поставки.
 *
 * Атрибуты класса:
 * timeZoneOffset - смещение часового пояса относительно GMT для местоположения этого устройства в минутах;
 * serialNumber - серийный номер устройства;
 * measurement - измеряемый параметр.
 */
public class Meter extends IdentifiedObject {

    private final Double timeZoneOffset;
    private final String serialNumber;
    private final Measurement measurement;

    public Meter(Double timeZoneOffset,
                 String serialNumber,
                 String unitSymbol,
                 String unitMultiplier,
                 String measurementType) {
        super();
        this.timeZoneOffset = timeZoneOffset;
        this.serialNumber = serialNumber;
        this.measurement = new Measurement(unitSymbol, unitMultiplier, measurementType) {
        };
    }

    public Double getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    @Override
    public String toString() {
        return "Meter{" +
                "mRID=" + this.getmRID() + ", " +
                "serialNumber='" + this.serialNumber + ", " +
                "unitSymbol='" + this.getMeasurement().getUnitSymbol() + ", " +
                "unitMultiplier='" + this.getMeasurement().getUnitMultiplier() + ", " +
                '}';
    }

}
