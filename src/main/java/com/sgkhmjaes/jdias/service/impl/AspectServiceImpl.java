package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
import com.sgkhmjaes.jdias.repository.search.ContactSearchRepository;
import com.sgkhmjaes.jdias.service.ContactService;
import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.service.UserService;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Aspect.
 */
@Service
@Transactional
public class AspectServiceImpl implements AspectService{

    private final Logger log = LoggerFactory.getLogger(AspectServiceImpl.class);
    private final AspectRepository aspectRepository;
    private final AspectSearchRepository aspectSearchRepository;
    private final UserService userService;
    private final PersonService personService;
    private final ContactSearchRepository contactSearchRepository;
    private final ContactRepository contactRepository;

    public AspectServiceImpl(AspectRepository aspectRepository, AspectSearchRepository aspectSearchRepository,
            UserService userService, PersonService personService, ContactService contactService,
            ContactRepository contactRepository, ContactSearchRepository contactSearchRepository) {
        this.aspectRepository = aspectRepository;
        this.aspectSearchRepository = aspectSearchRepository;
        this.userService = userService;
        this.personService = personService;
        this.contactSearchRepository = contactSearchRepository;
        this.contactRepository= contactRepository;
    }

    /**se
     * Save a aspect.
     *
     * @param aspect the entity to save
     * @return the persisted entity
     */
    @Override
    public Aspect save(Aspect aspect) {
        log.debug("Request to save Aspect : {}", aspect);

        if (aspect.getName() == null)
            aspect.setName(aspectDefaultName);
        String aspectName = aspect.getName().trim();
        if (aspectName.isEmpty())
            aspect.setName(aspectDefaultName);
        else aspect.setName(aspectName);

        Person person = userService.getCurrentPerson();

        for (Aspect personAspect : person.getAspects()) {
            if (personAspect.getName().equals(aspect.getName())) {
                person.removeAspect(personAspect);
                personAspect.setUpdatedAt(ZonedDateTime.now());
                personAspect.setContactVisible(aspect.getContactVisible());
                personAspect.setChatEnabled(aspect.getChatEnabled());
                personAspect.setPostDefault(aspect.getPostDefault());
                person.addAspect(personAspect);

                Aspect result = aspectRepository.save(personAspect);
                aspectSearchRepository.save(result);

                personService.save(person);
                return result;
            }
        }
        aspect.setCreatedAt(LocalDate.now());
        aspect.setUpdatedAt(ZonedDateTime.now());

        person.addAspect(aspect);

        Aspect result = aspectRepository.save(aspect);
        aspectSearchRepository.save(result);

        personService.save(person);
        return result;
    }

    /*/**
     *  Get all the aspects.
     *
     *  @return the list of entities
     *
    @Override
    @Transactional(readOnly = true)
    public List<Aspect> findAll() {
        log.debug("Request to get all Aspects");
        return aspectRepository.findAll();
    }*/

    @Override
    public Set<Aspect> findAllByUser() {
        log.debug("Request to get all Aspects by user");
        Set<Aspect> aspects = userService.getCurrentPerson().getAspects();
        return aspects;
    }

    /**
     *  Get one aspect by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Aspect findOne(Long id) {
        log.debug("Request to get Aspect : {}", id);
        Aspect foundAspect =  aspectRepository.findOne(id);
        if(userService.getCurrentPerson().getAspects().contains(foundAspect)) return foundAspect;
        else return null;
    }

    /**
     *  Delete the  aspect by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aspect : {}", id);
        Aspect foundAspect =  aspectRepository.findOne(id);
        Person currentPerson = userService.getCurrentPerson();

        if(currentPerson.getAspects().contains(foundAspect)) {
            Set<Contact> contacts = foundAspect.getContacts();
            for (Contact contact : contacts) {
                contact.setAspect(null);
                contactSearchRepository.save(contactRepository.save(contact));
            }
            currentPerson.removeAspect(foundAspect);
            aspectRepository.delete(id);
            aspectSearchRepository.delete(id);
            personService.save(currentPerson);
        }
    }

    /**
     * Search for the aspect corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<Aspect> search(String query) {
        log.debug("Request to search Aspects for query {}", query);
        Set <Aspect> foundAspects = new HashSet <>();
        Set<Aspect> currentPersonAspects = userService.getCurrentPerson().getAspects();
        Iterable<Aspect> search = aspectSearchRepository.search(queryStringQuery(query));
        Iterator<Aspect> iterator = search.iterator();
        while (iterator.hasNext()) {
            Aspect aspect = iterator.next();
            if (currentPersonAspects.contains(aspect)) foundAspects.add(aspect);
        }
        return foundAspects;
    }
}
