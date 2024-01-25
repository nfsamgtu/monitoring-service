package com.smetering.domain.entities.enums;


public enum UnitSymbol {

    NONE(""),
    WH("Вт*ч"),
    M3("м^3");

    private final String title;

    UnitSymbol(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
