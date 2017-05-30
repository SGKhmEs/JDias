package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.AspectVisiblityService;
import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import com.sgkhmjaes.jdias.repository.AspectVisiblityRepository;
import com.sgkhmjaes.jdias.repository.search.AspectVisiblitySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AspectVisiblity.
 */
@Service
@Transactional
public class AspectVisiblityServiceImpl implements AspectVisiblityService{

    private final Logger log = LoggerFactory.getLogger(AspectVisiblityServiceImpl.class);

    private final AspectVisiblityRepository aspectVisiblityRepository;

    private final AspectVisiblitySearchRepository aspectVisiblitySearchRepository;

    public AspectVisiblityServiceImpl(AspectVisiblityRepository aspectVisiblityRepository, AspectVisiblitySearchRepository aspectVisiblitySearchRepository) {
        this.aspectVisiblityRepository = aspectVisiblityRepository;
        this.aspectVisiblitySearchRepository = aspectVisiblitySearchRepository;
    }

    /**
     * Save a aspectVisiblity.
     *
     * @param aspectVisiblity the entity to save
     * @return the persisted entity
     */
    @Override
    public AspectVisiblity save(AspectVisiblity aspectVisiblity) {
        log.debug("Request to save AspectVisiblity : {}", aspectVisiblity);
        AspectVisiblity result = aspectVisiblityRepository.save(aspectVisiblity);
        aspectVisiblitySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the aspectVisiblities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectVisiblity> findAll() {
        log.debug("Request to get all AspectVisiblities");
        return aspectVisiblityRepository.findAll();
    }

    /**
     *  Get one aspectVisiblity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AspectVisiblity findOne(Long id) {
        log.debug("Request to get AspectVisiblity : {}", id);
        return aspectVisiblityRepository.findOne(id);
    }

    /**
     *  Delete the  aspectVisiblity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AspectVisiblity : {}", id);
        aspectVisiblityRepository.delete(id);
        aspectVisiblitySearchRepository.delete(id);
    }

    /**
     * Search for the aspectVisiblity corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AspectVisiblity> search(String query) {
        log.debug("Request to search AspectVisiblities for query {}", query);
        return StreamSupport
            .stream(aspectVisiblitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
