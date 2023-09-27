package com.fabriciosaand.cursorestfulapis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fabriciosaand.cursorestfulapis.model.Person;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Person findById(String id) {

        logger.info("PersonService :: findById | Finding on person");

        Person person = new Person();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Fabricio");
        person.setLastName("Andrade");
        person.setAddress("Av. Alphaville, Alphaville I, Salvador - Bahia");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll() {

        logger.info("PersonService :: findAll | Finding all people");

        List<Person> listPersons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person person = mockPerson(i);
            listPersons.add(person);
        }

        return listPersons;
    }

    public Person create(Person person) {

        logger.info("PersonService :: create | Creating one people");

        person.setId(counter.incrementAndGet());

        return person;
    }

    public Person update(Person person) {

        logger.info("PersonService :: update | Updating one people");

        person.setId(counter.incrementAndGet());

        return person;
    }

    public void delete(String id) {

        logger.info("PersonService :: delete | Deleting one people");

    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Person First Name " + i);
        person.setLastName("Person Last Name " + i);
        person.setAddress("Person Address " + i);
        person.setGender("Male");
        return person;
    }

}
