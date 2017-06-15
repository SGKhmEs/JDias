package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.ContactService;
import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserAccountRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.repository.search.ContactSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);
    private final ContactRepository contactRepository;
    private final ContactSearchRepository contactSearchRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public ContactServiceImpl(ContactRepository contactRepository, ContactSearchRepository contactSearchRepository, 
            UserRepository userRepository, PersonRepository personRepository) {
        this.contactRepository = contactRepository;
        this.contactSearchRepository = contactSearchRepository;
        this.userRepository=userRepository;
        this.personRepository=personRepository;
    }

    /**
     * Save a contact.
     *
     * @param contact the entity to save
     * @return the persisted entity
     */
    @Override
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the contacts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        log.debug("Request to get all Contacts");
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        List <Contact> contacts = new ArrayList <>(person.getContacts());
        return contacts;
        //return contactRepository.findAll();
    }

    /**
     * Get one contact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contact findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findOne(id);
    }

    /**
     * Delete the contact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.delete(id);
        contactSearchRepository.delete(id);
    }

    /**
     * Search for the contact corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> search(String query) {
        log.debug("Request to search Contacts for query {}", query);
        return StreamSupport
                .stream(contactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
