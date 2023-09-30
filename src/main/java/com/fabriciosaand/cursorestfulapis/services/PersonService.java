package com.fabriciosaand.cursorestfulapis.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabriciosaand.cursorestfulapis.exceptions.ResourceNotFoundException;
import com.fabriciosaand.cursorestfulapis.model.Person;
import com.fabriciosaand.cursorestfulapis.repository.PersonRepository;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonRepository repository;

    public Person findById(Long id) {

        logger.info("PersonService :: findById | Finding on person");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    public List<Person> findAll() {

        logger.info("PersonService :: findAll | Finding all people");

        return repository.findAll();

    }

    public Person create(Person person) {

        logger.info("PersonService :: create | Creating one people");

        return repository.save(person);
    }

    public Person update(Person person) {

        logger.info("PersonService :: update | Updating one people");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id) {

        logger.info("PersonService :: delete | Deleting one people");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        repository.delete(entity);

    }

}
