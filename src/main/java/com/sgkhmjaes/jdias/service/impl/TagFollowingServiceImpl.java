package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.TagFollowingService;
import com.sgkhmjaes.jdias.domain.TagFollowing;
import com.sgkhmjaes.jdias.repository.TagFollowingRepository;
import com.sgkhmjaes.jdias.repository.search.TagFollowingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TagFollowing.
 */
@Service
@Transactional
public class TagFollowingServiceImpl implements TagFollowingService {

    private final Logger log = LoggerFactory.getLogger(TagFollowingServiceImpl.class);

    private final TagFollowingRepository tagFollowingRepository;

    private final TagFollowingSearchRepository tagFollowingSearchRepository;

    public TagFollowingServiceImpl(TagFollowingRepository tagFollowingRepository, TagFollowingSearchRepository tagFollowingSearchRepository) {
        this.tagFollowingRepository = tagFollowingRepository;
        this.tagFollowingSearchRepository = tagFollowingSearchRepository;
    }

    /**
     * Save a tagFollowing.
     *
     * @param tagFollowing the entity to save
     * @return the persisted entity
     */
    @Override
    public TagFollowing save(TagFollowing tagFollowing) {
        log.debug("Request to save TagFollowing : {}", tagFollowing);
        TagFollowing result = tagFollowingRepository.save(tagFollowing);
        tagFollowingSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tagFollowings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagFollowing> findAll() {
        log.debug("Request to get all TagFollowings");
        return tagFollowingRepository.findAll();
    }

    /**
     * Get one tagFollowing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TagFollowing findOne(Long id) {
        log.debug("Request to get TagFollowing : {}", id);
        return tagFollowingRepository.findOne(id);
    }

    /**
     * Delete the tagFollowing by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagFollowing : {}", id);
        tagFollowingRepository.delete(id);
        tagFollowingSearchRepository.delete(id);
    }

    /**
     * Search for the tagFollowing corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagFollowing> search(String query) {
        log.debug("Request to search TagFollowings for query {}", query);
        return StreamSupport
                .stream(tagFollowingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
