package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.TaggingService;
import com.sgkhmjaes.jdias.domain.Tagging;
import com.sgkhmjaes.jdias.repository.TaggingRepository;
import com.sgkhmjaes.jdias.repository.search.TaggingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tagging.
 */
@Service
@Transactional
public class TaggingServiceImpl implements TaggingService{

    private final Logger log = LoggerFactory.getLogger(TaggingServiceImpl.class);
    private final TaggingRepository taggingRepository;
    private final TaggingSearchRepository taggingSearchRepository;

    public TaggingServiceImpl(TaggingRepository taggingRepository, TaggingSearchRepository taggingSearchRepository) {
        this.taggingRepository = taggingRepository;
        this.taggingSearchRepository = taggingSearchRepository;
    }

    /**
     * Save a tagging.
     *
     * @param tagging the entity to save
     * @return the persisted entity
     */
    @Override
    public Tagging save(Tagging tagging) {
        log.debug("Request to save Tagging : {}", tagging);
        Tagging result = taggingRepository.save(tagging);
        taggingSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the taggings.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tagging> findAll() {
        log.debug("Request to get all Taggings");
        return taggingRepository.findAll();
    }

    /**
     *  Get one tagging by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tagging findOne(Long id) {
        log.debug("Request to get Tagging : {}", id);
        return taggingRepository.findOne(id);
    }

    /**
     *  Delete the  tagging by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tagging : {}", id);
        taggingRepository.delete(id);
        taggingSearchRepository.delete(id);
    }

    /**
     * Search for the tagging corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tagging> search(String query) {
        log.debug("Request to search Taggings for query {}", query);
        return StreamSupport
            .stream(taggingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
