package com.reba.person.domain.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentTypeEnumTest {

    @Test
    public void testDNI() {
        assertEquals("Documento Nacional de Identidad", DocumentTypeEnum.DNI.getDescription());
    }

    @Test
    public void testLE() {
        assertEquals("Libreta de Enrolamiento", DocumentTypeEnum.LE.getDescription());
    }

    @Test
    public void testLC() {
        assertEquals("Libreta Cívica", DocumentTypeEnum.LC.getDescription());
    }

    @Test
    public void testCI() {
        assertEquals("Cédula de Identidad", DocumentTypeEnum.CI.getDescription());
    }
}