package com.reba.person.application.service;

import com.reba.person.domain.model.ContactData;
import com.reba.person.domain.model.Person;
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

    public Person getPersonById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person createPerson(Person person) {
        if (!is18YearsOld(person)) {
            throw new IllegalArgumentException("Person must be at least 18 years old");
        }
        return personRepository.save(person);
    }

    private boolean is18YearsOld(Person person) {
        var period = Period.between(person.getBirthDate(), LocalDate.now());
        return period.getYears() >= 18;
    }

    public Person updatePerson(UUID id, Person person) {
        if (personRepository.existsById(id.toString())) {
            // Add any necessary business logic before updating the person
            person.setId(id); // Ensure the ID is set for the update
            return personRepository.save(person);
        } else {
            return null; // Person with the given ID not found
        }
    }

    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }
}
