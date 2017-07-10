package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
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
    private final String aspectDefultName = "My aspect";
    private final AspectRepository aspectRepository;
    private final AspectSearchRepository aspectSearchRepository;
    private final UserService userService;
    private final PersonService personService;

    public AspectServiceImpl(AspectRepository aspectRepository, AspectSearchRepository aspectSearchRepository,
            UserService userService, PersonService personService) {
        this.aspectRepository = aspectRepository;
        this.aspectSearchRepository = aspectSearchRepository;
        this.userService = userService;
        this.personService = personService;
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
        
        if (aspect.getName() == null)aspect.setName(aspectDefultName);
        String aspectName = aspect.getName().trim();
        if (aspectName.isEmpty()) aspect.setName(aspectDefultName);
        else aspect.setName(aspectName);
        
        Person person = userService.getCurrentPerson();
        Set<Aspect> aspects = person.getAspects();
        aspects.contains(aspect);
        
        for (Aspect pesonAspect : person.getAspects()) {
            if (pesonAspect.getName().equals(aspect.getName())) {
                person.removeAspect(pesonAspect);
                pesonAspect.setUpdatedAt(ZonedDateTime.now());
                pesonAspect.setContactVisible(aspect.getContactVisible());
                pesonAspect.setChatEnabled(aspect.getChatEnabled());
                pesonAspect.setPostDefault(aspect.getPostDefault());
                person.addAspect(pesonAspect);
                
                Aspect result = aspectRepository.save(pesonAspect);
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
        return userService.getCurrentPerson().getAspects();
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
        
        if(userService.getCurrentPerson().getAspects().contains(foundAspect))
            return foundAspect;
        else return new Aspect();
        // or return new UnauthorizedException
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

        if(userService.getCurrentPerson().getAspects().contains(foundAspect)) {
            Person person = userService.getCurrentPerson();
            if(person.getAspects().contains(foundAspect)) person.removeAspect(foundAspect);
            aspectRepository.delete(id);
            aspectSearchRepository.delete(id);
            personService.save(person);
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
        Set <Aspect> findAspects = new HashSet <>();
        Set<Aspect> aspects = userService.getCurrentPerson().getAspects();
        Iterable<Aspect> search = aspectSearchRepository.search(queryStringQuery(query));
        Iterator<Aspect> iterator = search.iterator();
        while (iterator.hasNext()) {
            Aspect aspect = iterator.next();
            if (aspects.contains(aspect)) findAspects.add(aspect);
        }
        return findAspects;
    }
}