package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.HashTagService;
import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.repository.HashTagRepository;
import com.sgkhmjaes.jdias.repository.search.HashTagSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HashTag.
 */
@Service
@Transactional
public class HashTagServiceImpl implements HashTagService{

    private final Logger log = LoggerFactory.getLogger(HashTagServiceImpl.class);

    private final HashTagRepository hashTagRepository;

    private final HashTagSearchRepository hashTagSearchRepository;

    public HashTagServiceImpl(HashTagRepository hashTagRepository, HashTagSearchRepository hashTagSearchRepository) {
        this.hashTagRepository = hashTagRepository;
        this.hashTagSearchRepository = hashTagSearchRepository;
    }

    /**
     * Save a hashTag.
     *
     * @param hashTag the entity to save
     * @return the persisted entity
     */
    @Override
    public HashTag save(HashTag hashTag) {
        log.debug("Request to save HashTag : {}", hashTag);
        HashTag result = hashTagRepository.save(hashTag);
        hashTagSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the hashTags.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HashTag> findAll() {
        log.debug("Request to get all HashTags");
        return hashTagRepository.findAll();
    }

    /**
     *  Get one hashTag by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HashTag findOne(Long id) {
        log.debug("Request to get HashTag : {}", id);
        return hashTagRepository.findOne(id);
    }

    /**
     *  Delete the  hashTag by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HashTag : {}", id);
        hashTagRepository.delete(id);
        hashTagSearchRepository.delete(id);
    }

    /**
     * Search for the hashTag corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HashTag> search(String query) {
        log.debug("Request to search HashTags for query {}", query);
        return StreamSupport
            .stream(hashTagSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
