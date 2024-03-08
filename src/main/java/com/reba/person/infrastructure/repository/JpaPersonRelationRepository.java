package com.reba.person.infrastructure.repository;

import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.PersonRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonRelationRepository extends JpaRepository<PersonRelation, String> {
}
