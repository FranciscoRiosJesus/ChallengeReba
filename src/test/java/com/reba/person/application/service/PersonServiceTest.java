package com.reba.person.application.service;

import com.reba.person.domain.model.ContactData;
import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import com.reba.person.infrastructure.repository.JpaPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonServiceTest {
    @Mock
    private JpaPersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPersons() {
        List<Person> mockPersons = List.of(
                new Person(null, "Juan", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322"))),
                new Person(null, "Jane", DocumentTypeEnum.CI, "9876543210", CountryEnum.USA, Date.from(LocalDate.parse("2007-04-10").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+14155556666")))
        );
        when(personRepository.findAll()).thenReturn(mockPersons);

        List<Person> result = personService.getAllPersons();

        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getName());
        assertEquals(DocumentTypeEnum.CI, result.get(0).getDocumentType());
        assertEquals("1234567890", result.get(0).getDocumentNumber());
        assertEquals(CountryEnum.BRA, result.get(0).getCountry());
        assertEquals(Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), result.get(0).getBirthDate());
        assertEquals(List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")), result.get(0).getContactData());
    }

    @Test
    public void testGetPersonById() {
        // Given
        Person mockPerson = new Person(null, "John", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from((LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant())), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personRepository.findById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(java.util.Optional.of(mockPerson));

        // When
        Person result = personService.getPersonById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"));

        // Then
        assertEquals("John", result.getName());
        assertEquals(DocumentTypeEnum.CI, result.getDocumentType());
        assertEquals("1234567890", result.getDocumentNumber());
        assertEquals(CountryEnum.BRA, result.getCountry());
        assertEquals(Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), result.getBirthDate());
        assertEquals(List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")), result.getContactData());
    }

    @Test
    public void testCreatePerson() {
        // Given
        Person newPerson = new Person(null, "Alice", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personRepository.save(any(Person.class))).thenReturn(newPerson);

        // When
        Person savedPerson = personService.createPerson(newPerson);

        // Then
        assertEquals("Alice", savedPerson.getName());
        assertEquals(DocumentTypeEnum.CI, savedPerson.getDocumentType());
        assertEquals("1234567890", savedPerson.getDocumentNumber());
        assertEquals(CountryEnum.BRA, savedPerson.getCountry());
        assertEquals(Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), savedPerson.getBirthDate());
        assertEquals(List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")), savedPerson.getContactData());
    }

    @Test
    public void testCreatePersonUnder18YearsOld() {
        Person newPerson = new Person(null, "Alice", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2010-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));

        assertThrows(IllegalArgumentException.class, () -> personService.createPerson(newPerson));
    }

    @Test
    public void testUpdatePerson() {
        var existingPerson = new Person(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), "Juan", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);
        when(personRepository.existsById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(true);

        Person updatedPerson = existingPerson;
        updatedPerson.setName("Juan Perez");
        updatedPerson.setBirthDate(Date.from(LocalDate.parse("2001-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Person result = personService.updatePerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), updatedPerson);

        assertEquals("Juan Perez", result.getName());
        assertEquals(DocumentTypeEnum.CI, result.getDocumentType());
        assertEquals("1234567890", result.getDocumentNumber());
        assertEquals(CountryEnum.BRA, result.getCountry());
        assertEquals(Date.from(LocalDate.parse("2001-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), result.getBirthDate());
        assertEquals(List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")), result.getContactData());
    }

    @Test
    public void testPatchPerson() {
        var existingPerson = new Person(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), "Juan", DocumentTypeEnum.CI, "1234567890", CountryEnum.BRA, Date.from(LocalDate.parse("2006-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")));
        when(personRepository.findById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(Optional.of(existingPerson));
        when(personRepository.existsById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"))).thenReturn(true);

        Person updatedPerson = existingPerson;
        updatedPerson.setName("Juan Perez");
        updatedPerson.setBirthDate(Date.from(LocalDate.parse("2001-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);
        Person result = personService.patchPerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"), updatedPerson);

        assertEquals("Juan Perez", result.getName());
        assertEquals(DocumentTypeEnum.CI, result.getDocumentType());
        assertEquals("1234567890", result.getDocumentNumber());
        assertEquals(CountryEnum.BRA, result.getCountry());
        assertEquals(Date.from(LocalDate.parse("2001-03-09").atStartOfDay(ZoneId.systemDefault()).toInstant()), result.getBirthDate());
        assertEquals(List.of(new ContactData(ContactDataTypeEnum.PHONE, "+5491155443322")), result.getContactData());
    }

    @Test
    public void testDeletePerson() {
        personService.deletePerson(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"));
        verify(personRepository, times(1)).deleteById(UUID.fromString("2a752c82-6019-4c14-a24d-43aa17a5a18f"));
    }
}