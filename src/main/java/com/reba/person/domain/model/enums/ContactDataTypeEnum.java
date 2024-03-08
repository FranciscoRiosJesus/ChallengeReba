package com.reba.person.domain.model.enums;

public enum ContactDataTypeEnum {
    EMAIL("Email"),
    PHONE("Phone");

    private final String name;

    ContactDataTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
