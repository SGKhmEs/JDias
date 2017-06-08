package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.search.PersonSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService{

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    private final PersonSearchRepository personSearchRepository;

    public PersonServiceImpl(PersonRepository personRepository, PersonSearchRepository personSearchRepository) {
        this.personRepository = personRepository;
        this.personSearchRepository = personSearchRepository;
    }

    /**
     * Save a person.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    @Override
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        Person result = personRepository.save(person);
        personSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the people.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.debug("Request to get all People");
        return personRepository.findAll();
    }

    /**
     *  Get one person by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Person findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findOne(id);
    }

    /**
     *  Delete the  person by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.delete(id);
        personSearchRepository.delete(id);
    }

    /**
     * Search for the person corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> search(String query) {
        log.debug("Request to search People for query {}", query);
        return StreamSupport
            .stream(personSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
