package com.smetering.domain.entities.enums;

public enum UnitMultiplier {

    PICO("п"),
    NANO("н"),
    MICRO("мк"),
    CENTI("с"),
    DECI("д"),
    KILO("к"),
    MEGA("М"),
    GIGA("Г"),
    TERA("Т"),
    NONE("");

    private final String title;

    UnitMultiplier(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
