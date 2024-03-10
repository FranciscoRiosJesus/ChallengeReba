package com.reba.person.application.service;

import com.reba.person.application.exception.PersonNotFoundException;
import com.reba.person.domain.model.ContactData;
import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.enums.ContactDataTypeEnum;
import com.reba.person.domain.model.enums.CountryEnum;
import com.reba.person.domain.model.enums.DocumentTypeEnum;
import com.reba.person.infrastructure.repository.JpaPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private JpaPersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(UUID id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        try {
            if (!is18YearsOld(person)) {
                throw new IllegalArgumentException("Person must be at least 18 years old");
            }
            return personRepository.save(person);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid person data");
        }
    }

    private boolean is18YearsOld(Person person) {
        var period = Period.between(person.getBirthDate(), LocalDate.now());
        return period.getYears() >= 18;
    }

    public Person updatePerson(UUID id, Person person) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            return personRepository.save(person);
        } else {
            return null;
        }
    }

    public void deletePerson(UUID id) {
        personRepository.deleteById(id);
    }

    public Person patchPerson(UUID id, Person person) {
        if (personRepository.existsById(id)) {
            Person existingPerson = personRepository.findById(id).orElse(null);

            if (person.getName() != null) {
                existingPerson.setName(person.getName());
            }
            if (person.getBirthDate() != null) {
                existingPerson.setBirthDate(person.getBirthDate());
            }
            if (person.getContactData() != null) {
                existingPerson.setContactData(person.getContactData());
            }
            if (person.getCountry() != null) {
                existingPerson.setCountry(person.getCountry());
            }
            if (person.getDocumentNumber() != null) {
                existingPerson.setDocumentNumber(person.getDocumentNumber());
            }
            if (person.getDocumentType() != null) {
                existingPerson.setDocumentType(person.getDocumentType());
            }
            existingPerson.setId(id);
            return personRepository.save(existingPerson);
        } else {
            throw new PersonNotFoundException("Person not found: " + id);
        }
    }

    public boolean existsById(UUID childId) {
        return personRepository.existsById(childId);
    }

    public List<Person> seed() {
        return personRepository.saveAll(List.of(
                new Person(
                        null,
                        "Juan",
                        DocumentTypeEnum.DNI,
                        "1234567890",
                        CountryEnum.ARG,
                        LocalDate.of(1990, 1, 1),
                        List.of(
                                new ContactData(
                                        ContactDataTypeEnum.EMAIL,
                                        "nVlqz@example.com"
                                ),
                                new ContactData(
                                        ContactDataTypeEnum.PHONE,
                                        "1234567890"
                                )
                        )
                ),
                new Person(
                        null,
                        "Juana",
                        DocumentTypeEnum.DNI,
                        "1234567891",
                        CountryEnum.BRA,
                        LocalDate.of(1995, 1, 1),
                        List.of(
                                new ContactData(
                                        ContactDataTypeEnum.EMAIL,
                                        "nVlqz@example.com"
                                ),
                                new ContactData(
                                        ContactDataTypeEnum.PHONE,
                                        "1234567890"
                                )
                        )
                ),
                new Person(
                        null,
                        "Jimena",
                        DocumentTypeEnum.DNI,
                        "1234567892",
                        CountryEnum.USA,
                        LocalDate.of(2000, 1, 1),
                        List.of(
                                new ContactData(
                                        ContactDataTypeEnum.EMAIL,
                                        "nVlqz@example.com"
                                ),
                                new ContactData(
                                        ContactDataTypeEnum.PHONE,
                                        "1234567890"
                                )
                        )
                ),
                new Person(
                        null,
                        "Javier",
                        DocumentTypeEnum.DNI,
                        "1234567893",
                        CountryEnum.ESP,
                        LocalDate.of(2005, 1, 1),
                        List.of(
                                new ContactData(
                                        ContactDataTypeEnum.EMAIL,
                                        "nVlqz@example.com"
                                ),
                                new ContactData(
                                        ContactDataTypeEnum.PHONE,
                                        "1234567890"
                                )
                        )
                ),
                new Person(
                        null,
                        "Jorge",
                        DocumentTypeEnum.DNI,
                        "1234567894",
                        CountryEnum.ESP,
                        LocalDate.of(2010, 1, 1),
                        List.of(
                                new ContactData(
                                        ContactDataTypeEnum.EMAIL,
                                        "nVlqz@example.com"
                                ),
                                new ContactData(
                                        ContactDataTypeEnum.PHONE,
                                        "1234567890"
                                )
                        )
                )
        ));
    }
}
