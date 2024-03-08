package com.reba.person.application;

import com.reba.person.application.service.PersonRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PersonRelationController {

    @Autowired
    private PersonRelationService personRelationService;

    @PostMapping("/persons/{id1}/parent/{id2}")
    public ResponseEntity<String> setParentRelation(@PathVariable UUID id1, @PathVariable UUID id2) {
        personRelationService.setParentRelation(id1, id2);
        return ResponseEntity.ok("Person with ID " + id1 + " is the parent of the person with ID " + id2);
    }

    @GetMapping("/relations/{id1}/{id2}")
    public ResponseEntity<String> getRelation(@PathVariable UUID id1, @PathVariable UUID id2) {
        String relation = personRelationService.getRelation(id1, id2);
        return ResponseEntity.ok(relation);
    }
}
