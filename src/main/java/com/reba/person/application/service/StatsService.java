package com.reba.person.application.service;

import com.reba.person.domain.model.Person;
import com.reba.person.domain.model.Stat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StatsService {

    @Autowired
    private PersonService personService;

    public List<Stat> getStats() {
        List<Person> persons = personService.getAllPersons();

        //Get number of people per country
        List<Stat> stats = persons
                .stream()
                .map(Person::getCountry)
                .distinct()
                .map(country -> new Stat(country, persons
                        .stream()
                        .filter(person -> person.getCountry().equals(country))
                        .count()))
                .toList();
        log.info("Stats: {}", stats);

        //Get percentage of people per country
        for (Stat stat : stats) {
            stat.setPercentage((float) persons
                    .stream()
                    .filter(person -> person.getCountry().equals(stat.getCountry()))
                    .count() / persons.size() * 100);
        }
        return stats;
    }
}
