package com.smetering.domain.entities;

import java.util.UUID;

/**
 * Идентифицирующий класс - обеспечивает идентификацию и набор наименований для
 * наследуемых классов и набор наименований для наследуемых классов.
 *
 * Атрибуты класса:
 * name - наименование объекта информационной модели;
 * aliasName - дополнительное наименование идентифицируемого объекта;
 * description - описание объекта информационной модели;
 * mRID - глобальный уникальный идентификатор объекта информационной модели.
 */
public abstract class IdentifiedObject {
    private String name;
    private String aliasName;
    private String description;
    private final UUID mRID;

    protected IdentifiedObject() {
        this.mRID = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getmRID() {
        return mRID;
    }

}
