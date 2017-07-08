package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.ContactService;
import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.repository.search.ContactSearchRepository;
import com.sgkhmjaes.jdias.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService{

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;
    private final UserService userService;

    private final ContactSearchRepository contactSearchRepository;

    public ContactServiceImpl(ContactRepository contactRepository,
                              ContactSearchRepository contactSearchRepository,
                              UserService userService) {
        this.contactRepository = contactRepository;
        this.contactSearchRepository = contactSearchRepository;
        this.userService = userService;
    }

  /*  /**
     * Save a contact.
     *
     * @param contact the entity to save
     * @return the persisted entity
     *
    @Override
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return result;
    }*/

    /**
     *  Get all the contacts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAllByUser() {
        log.debug("Request to get all Contacts by user");
        return new ArrayList<>(userService.getCurrentPerson().getContacts());
    }

  /*  /**
     *  Get all the contacts.
     *
     *  @return the list of entities
     *
    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll();
    }*/

    /**
     *  Get one contact by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contact findOne(Long id) {
        log.debug("Request to get Contact : {}", id);

        Contact contact = contactRepository.findOne(id);

        if(userService.getCurrentPerson().getContacts().contains(contact))
            return contact;
        else return null;
    }

    /**
     *  Delete the  contact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);

        Contact contact = contactRepository.findOne(id);

        if(userService.getCurrentPerson().getContacts().contains(contact)) {

            Person person = contact.getPerson();
            if(person.getContacts().contains(contact))
                person.removeContacts(contact);

            Aspect aspect = contact.getAspect();
            if(aspect.getContacts().contains(contact))
                aspect.removeContact(contact);

            contactRepository.delete(id);
            contactSearchRepository.delete(id);
        }
    }

    /**
     * Search for the contact corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> search(String query) {
        log.debug("Request to search Contacts for query {}", query);

        List<Contact> foundContactList = StreamSupport
            .stream(contactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());

        List<Contact> usersContactList = new ArrayList<>(userService.getCurrentPerson().getContacts());

        foundContactList.forEach(aspect -> {
            if(!usersContactList.contains(aspect))
                foundContactList.remove(aspect);
        });
        return foundContactList;

    }
}
