package com.reba.person.infrastructure.repository;

import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.PersonRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaPersonRelationRepository extends JpaRepository<PersonRelation, UUID> {
    boolean existsByParentIdAndChildId(UUID parentId, UUID childId);

    List<PersonRelation> findAllByChildId(UUID id1);
}
