package com.reba.person.application.service;

import com.reba.person.domain.model.PersonRelation;
import com.reba.person.infrastructure.repository.JpaPersonRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonRelationService {

    @Autowired
    private JpaPersonRelationRepository personRelationRepository;

    public void setParentRelation(UUID parentId, UUID childId) {
        personRelationRepository.save(new PersonRelation(null, parentId, childId));
    }

    public String getRelation(UUID id1, UUID id2) {
        //if both has the same parent return sibling
        if (isSibling(id1, id2)) {
            return "SIBLING";
        }
        //if both has the same grandparent return cousin
        if (isCousin(id1, id2)) {
            return "COUSIN";
        }
        //if the parent of one is the grandparent of the other return uncle
        if (isUncle(id1, id2) || isUncle(id2, id1)) {
            return "UNCLE";
        }
        return "UNKNOWN";
    }

    private boolean isUncle(UUID id1, UUID id2) {
        return false;
    }

    private boolean isCousin(UUID id1, UUID id2) {
        return false;
    }

    private boolean isSibling(UUID id1, UUID id2) {
        return false;
    }
}
