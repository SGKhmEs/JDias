package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.ContactService;
import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.repository.search.ContactSearchRepository;
import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.service.UserService;
import java.util.HashSet;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.hibernate.Hibernate;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService{

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;
    private final ContactSearchRepository contactSearchRepository;
    private final UserService userService;
    private final PersonService personService;

    public ContactServiceImpl(ContactRepository contactRepository, ContactSearchRepository contactSearchRepository, 
            PersonService personService, UserService userService) {
        this.contactRepository = contactRepository;
        this.contactSearchRepository = contactSearchRepository;
        this.userService = userService;
        this.personService = personService;
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
        
        Person recipientContact = contact.getPerson();
        Person currentPerson = userService.getCurrentPerson();
        if (currentPerson.equals(contact.getPerson())) {
            // for testing: need replase Person to Contact
            contact.setPerson(getRecipientPerson(contact));
            recipientContact = contact.getPerson();
            if (currentPerson.equals(contact.getPerson())) return new Contact(); 
            //return new Contact();
        }
        
        contact.setOwnId(recipientContact.getId());
        contact.setRecipient(recipientContact.getDiasporaId());
        contact.setAuthor(currentPerson.getDiasporaId());
        contact.setPerson(currentPerson);
        
        if (!currentPerson.getContacts().contains(contact)) {
            currentPerson.addContacts(contact);
            personService.save(currentPerson);
        } 
        
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        
        return result;

    }

    /**
     *  Get all the contacts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<Contact> findAll() {
        log.debug("Request to get all Contacts");
        Person currentPerson = userService.getCurrentPerson();
        Set<Contact> contacts = currentPerson.getContacts();
        Hibernate.initialize(contacts);
        return contacts;
    }

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
        Person currentPerson = userService.getCurrentPerson();
        Set<Contact> contacts = currentPerson.getContacts();
        Contact findContact = contactRepository.findOne(id);
        if (contacts.contains(findContact)) return findContact;
        else return new Contact();
    }

    /**
     *  Delete the  contact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        Person currentPerson = userService.getCurrentPerson();
        Set<Contact> contacts = currentPerson.getContacts();
        Contact findContact = contactRepository.findOne(id);
        if (contacts.contains(findContact)) {
            contacts.remove(findContact);
            currentPerson.setContacts(contacts);
            personService.save(currentPerson);
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
    public Set<Contact> search(String query) {
        log.debug("Request to search Contacts for query {}", query);
        Set <Contact> findContacts = new HashSet<>();
        Person currentPerson = userService.getCurrentPerson();
        Set<Contact> contacts = currentPerson.getContacts();
        
        Iterable<Contact> search = contactSearchRepository.search(queryStringQuery(query));
        Iterator<Contact> iterator = search.iterator();
        while (iterator.hasNext()) {
            Contact findContact = iterator.next();
            if (contacts.contains(findContact)) findContacts.add(findContact);
        }
        return findContacts;
        /*
        return StreamSupport
            .stream(contactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());*/
    }
    
    public Person getRecipientPerson (Contact contact) {
        return personService.findOne(contact.getOwnId());
    }
}
