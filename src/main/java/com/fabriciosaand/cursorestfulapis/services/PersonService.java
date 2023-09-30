package com.fabriciosaand.cursorestfulapis.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabriciosaand.cursorestfulapis.data.vo.v1.PersonVO;
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

        return DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {

        logger.info("PersonService :: findById | Finding on person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {

        logger.info("PersonService :: create | Creating one people");

        var entity = DozerMapper.parseObject(person, Person.class);

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public PersonVO update(PersonVO person) {

        logger.info("PersonService :: update | Updating one people");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public void delete(Long id) {

        logger.info("PersonService :: delete | Deleting one people");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        repository.delete(entity);

    }

}
