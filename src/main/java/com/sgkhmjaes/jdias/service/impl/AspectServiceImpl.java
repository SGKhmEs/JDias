package com.sgkhmjaes.jdias.service.impl;

import com.google.common.collect.Lists;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.UserAccount;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserAccountRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
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
 * Service Implementation for managing Aspect.
 */
@Service
@Transactional
public class AspectServiceImpl implements AspectService{

    private final Logger log = LoggerFactory.getLogger(AspectServiceImpl.class);

    private final AspectRepository aspectRepository;
    private final AspectSearchRepository aspectSearchRepository;
    private final UserService userService;

    public AspectServiceImpl(AspectRepository aspectRepository,
                             AspectSearchRepository aspectSearchRepository,
                             UserService userService) {
        this.aspectRepository = aspectRepository;
        this.aspectSearchRepository = aspectSearchRepository;
        this.userService = userService;
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
        Aspect result = aspectRepository.save(aspect);
        aspectSearchRepository.save(result);
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
    public List<Aspect> findAllByUser() {
        log.debug("Request to get all Aspects by user");
        return new ArrayList<>(userService.getCurrentPerson().getAspects());
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
        else return null;
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
            aspectRepository.delete(id);
            aspectSearchRepository.delete(id);
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
    public List<Aspect> search(String query) {
        log.debug("Request to search Aspects for query {}", query);

        List<Aspect> foundAspectList =  StreamSupport.stream(aspectSearchRepository.search(queryStringQuery(query))
            .spliterator(), false)
            .collect(Collectors.toList());

        List<Aspect> userAspect = new ArrayList<>(userService.getCurrentPerson().getAspects());

        foundAspectList.forEach(aspect ->{
            if(!userAspect.contains(aspect))
                foundAspectList.remove(aspect);
        });
        return foundAspectList;
    }
}
