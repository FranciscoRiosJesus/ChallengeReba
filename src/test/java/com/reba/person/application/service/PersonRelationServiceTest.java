package com.reba.person.application.service;

import com.reba.person.domain.model.ContactData;
import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.PersonRelation;
import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import com.reba.person.infrastructure.repository.JpaPersonRelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonRelationServiceTest {

    @Mock
    private JpaPersonRelationRepository personRelationRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonRelationService personRelationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetParentRelation() {
        when(personRelationRepository.save(any())).thenReturn(new PersonRelation(null, UUID.randomUUID(), UUID.randomUUID()));
        when(personRelationRepository.existsByParentIdAndChildId(any(), any())).thenReturn(false);
        when(personService.existsById(any())).thenReturn(true);
        when(personService.getPersonById(any())).thenReturn(new Person(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), "Juan", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322"))));

        PersonRelation personRelation = personRelationService.setParentRelation(UUID.randomUUID(), UUID.randomUUID());
        assertNotNull(personRelation);
    }

    @Test
    public void testGetRelation() {
        String relation = personRelationService.getRelation(UUID.randomUUID(), UUID.randomUUID());
        assertEquals("UNKNOWN", relation);
    }
}