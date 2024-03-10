package com.reba.person.application.service;

import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.PersonRelation;
import com.reba.person.infrastructure.repository.JpaPersonRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonRelationService {

    @Autowired
    private JpaPersonRelationRepository personRelationRepository;

    @Autowired
    private PersonService personService;

    public PersonRelation setParentRelation(UUID parentId, UUID childId) {
        if (personRelationRepository.existsByParentIdAndChildId(parentId, childId)) {
            throw new IllegalArgumentException("Person with ID " + childId + " already has the parent with ID " + parentId);
        }

        if (!existsPersonById(childId)) {
            throw new IllegalArgumentException("Person with ID " + childId + " does not exist");
        }

        if (!existsPersonById(parentId)) {
            throw new IllegalArgumentException("Person with ID " + parentId + " does not exist");
        }

        if (parentId.equals(childId)) {
            throw new IllegalArgumentException("Person with ID " + parentId + " and " + childId + " are the same");
        }
        return personRelationRepository.save(new PersonRelation(null, parentId, childId));
    }

    public String getRelation(UUID id1, UUID id2) {
        if (!existsPersonById(id1) || !existsPersonById(id2)) {
            return "UNKNOWN";
        }

        if (isSibling(id1, id2)) {
            return "SIBLING";
        }
        if (isCousin(id1, id2)) {
            return "COUSIN";
        }
        if (isUncle(id1, id2)) {
            return "UNCLE";
        }
        return "UNKNOWN";
    }

    private boolean existsPersonById(UUID id) {
        return personService.getPersonById(id) != null;
    }

    private boolean isUncle(UUID id1, UUID id2) {
        List<PersonRelation> personRelation1 = personRelationRepository.findAllByChildId(id1);
        List<PersonRelation> personRelation2 = personRelationRepository.findAllByChildId(id2);

        //the parent of one is the grandparent of the other
        for (var relation1 : personRelation1) {
            for (var relation2 : personRelation2) {
                for (var parent1 : personRelationRepository.findAllByChildId(relation1.getParentId())) {
                    if (parent1.getParentId().equals(relation2.getParentId())) {
                        return true;
                    }
                }
                for (var parent2 : personRelationRepository.findAllByChildId(relation2.getParentId())) {
                    if (parent2.getParentId().equals(relation1.getParentId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCousin(UUID id1, UUID id2) {
        List<PersonRelation> personRelation1 = personRelationRepository.findAllByChildId(id1);
        List<PersonRelation> personRelation2 = personRelationRepository.findAllByChildId(id2);

        //has the same grandparent
        for (var relation1 : personRelation1) {
            for (var relation2 : personRelation2) {
                if (isSibling(relation1.getParentId(), relation2.getParentId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSibling(UUID id1, UUID id2) {
        List<PersonRelation> personRelation1 = personRelationRepository.findAllByChildId(id1);
        List<PersonRelation> personRelation2 = personRelationRepository.findAllByChildId(id2);

        //has the same parent
        for (var relation1 : personRelation1) {
            for (var relation2 : personRelation2) {
                if (relation1.getParentId().equals(relation2.getParentId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<PersonRelation> seed() throws Exception {
        List<Person> persons = personService.getAllPersons();

        if (persons == null) {
            throw new Exception("Persons not found");
        }

        return personRelationRepository.saveAll(
                List.of(
                        new PersonRelation(null, persons.get(1).getId(), persons.get(0).getId()),
                        new PersonRelation(null, persons.get(2).getId(), persons.get(1).getId()),
                        new PersonRelation(null, persons.get(2).getId(), persons.get(3).getId()),
                        new PersonRelation(null, persons.get(3).getId(), persons.get(4).getId())
                )
        );
    }
}
