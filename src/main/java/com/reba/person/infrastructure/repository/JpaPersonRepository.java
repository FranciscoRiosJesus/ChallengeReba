package com.reba.person.infrastructure.repository;

import com.reba.person.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonRepository extends JpaRepository<Person, String> {
}
