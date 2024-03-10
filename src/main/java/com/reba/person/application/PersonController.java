package com.reba.person.application;

import com.reba.person.application.service.PersonService;
import com.reba.person.domain.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Endpoint to retrieve all persons
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    // Endpoint to retrieve a person by ID
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        Person person = personService.getPersonById(UUID.fromString(id));
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to create a new person
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person newPerson = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

    // Endpoint to update an existing person
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable UUID id, @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to update an existing person
    @PatchMapping("/{id}")
    public ResponseEntity<Person> patchPerson(@PathVariable UUID id, @RequestBody Person person) {
        Person updatedPerson = personService.patchPerson(id, person);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    //Seed endpoint
    @PostMapping("/seed")
    public ResponseEntity<List<Person>> seed() {
        List<Person> persons = personService.seed();
        return ResponseEntity.ok(persons);
    }
}