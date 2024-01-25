package com.smetering.domain.entities.enums;

public enum Role {

    ROLE_USER("Пользователь"),
    ROLE_ADMIN("Администратор");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
