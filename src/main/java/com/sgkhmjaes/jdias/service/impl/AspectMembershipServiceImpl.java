package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.AspectMembershipService;
import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.repository.AspectMembershipRepository;
import com.sgkhmjaes.jdias.repository.search.AspectMembershipSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AspectMembership.
 */
@Service
@Transactional
public class AspectMembershipServiceImpl implements AspectMembershipService{

    private final Logger log = LoggerFactory.getLogger(AspectMembershipServiceImpl.class);

    private final AspectMembershipRepository aspectMembershipRepository;

    private final AspectMembershipSearchRepository aspectMembershipSearchRepository;

    public AspectMembershipServiceImpl(AspectMembershipRepository aspectMembershipRepository, AspectMembershipSearchRepository aspectMembershipSearchRepository) {
        this.aspectMembershipRepository = aspectMembershipRepository;
        this.aspectMembershipSearchRepository = aspectMembershipSearchRepository;
    }

    /**
     * Save a aspectMembership.
     *
     * @param aspectMembership the entity to save
     * @return the persisted entity
     */
    @Override
    public AspectMembership save(AspectMembership aspectMembership) {
        log.debug("Request to save AspectMembership : {}", aspectMembership);
        AspectMembership result = aspectMembershipRepository.save(aspectMembership);
        aspectMembershipSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the aspectMemberships.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectMembership> findAll() {
        log.debug("Request to get all AspectMemberships");
        return aspectMembershipRepository.findAll();
    }

    /**
     *  Get one aspectMembership by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AspectMembership findOne(Long id) {
        log.debug("Request to get AspectMembership : {}", id);
        return aspectMembershipRepository.findOne(id);
    }

    /**
     *  Delete the  aspectMembership by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AspectMembership : {}", id);
        aspectMembershipRepository.delete(id);
        aspectMembershipSearchRepository.delete(id);
    }

    /**
     * Search for the aspectMembership corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectMembership> search(String query) {
        log.debug("Request to search AspectMemberships for query {}", query);
        return StreamSupport
            .stream(aspectMembershipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
