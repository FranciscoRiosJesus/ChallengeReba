package com.reba.person.domain.model.enums;

public enum DocumentTypeEnum {
    DNI("Documento Nacional de Identidad"),
    LE("Libreta de Enrolamiento"),
    LC("Libreta Cívica"),
    CI("Cédula de Identidad");

    private final String description;

    DocumentTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
