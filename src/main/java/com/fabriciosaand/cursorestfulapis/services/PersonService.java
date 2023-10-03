package com.fabriciosaand.cursorestfulapis.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.fabriciosaand.cursorestfulapis.controllers.PersonController;
import com.fabriciosaand.cursorestfulapis.data.vo.v1.PersonVO;
import com.fabriciosaand.cursorestfulapis.exceptions.RequiredObjectIsNullException;
import com.fabriciosaand.cursorestfulapis.exceptions.ResourceNotFoundException;
import com.fabriciosaand.cursorestfulapis.mapper.DozerMapper;
import com.fabriciosaand.cursorestfulapis.model.Person;
import com.fabriciosaand.cursorestfulapis.repository.PersonRepository;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonRepository repository;

    public List<PersonVO> findAll() {

        logger.info("PersonService :: findAll | Finding all people");

        var persons = DozerMapper.parseListObject(repository.findAll(), PersonVO.class);

        persons
            .stream()
            .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return persons;
    }

    public PersonVO findById(Long id) {

        logger.info("PersonService :: findById | Finding on person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("PersonService :: create | Creating one people");

        var entity = DozerMapper.parseObject(person, Person.class);

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("PersonService :: update | Updating one people");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("PersonService :: delete | Deleting one people");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        repository.delete(entity);

    }

}
