package com.reba.person.application;

import com.reba.person.application.service.PersonRelationService;
import com.reba.person.domain.model.PersonRelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PersonRelationControllerTest {
    @Mock
    private PersonRelationService personRelationService;

    @InjectMocks
    private PersonRelationController personRelationController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetParentRelation() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(personRelationService.setParentRelation(id1, id2)).thenReturn(new PersonRelation(null, id1, id2));

        ResponseEntity<String> response = personRelationController.setParentRelation(id1, id2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person with ID " + id1 + " is the parent of the person with ID " + id2, response.getBody());
    }

    @Test
    public void testGetRelation() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String relation = "parent";
        when(personRelationService.getRelation(id1, id2)).thenReturn(relation);

        ResponseEntity<String> response = personRelationController.getRelation(id1, id2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(relation, response.getBody());
    }
}