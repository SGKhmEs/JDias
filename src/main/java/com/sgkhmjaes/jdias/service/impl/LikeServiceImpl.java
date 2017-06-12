package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.LikeService;
import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.repository.LikeRepository;
import com.sgkhmjaes.jdias.repository.search.LikeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Like.
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    private final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;

    private final LikeSearchRepository likeSearchRepository;

    public LikeServiceImpl(LikeRepository likeRepository, LikeSearchRepository likeSearchRepository) {
        this.likeRepository = likeRepository;
        this.likeSearchRepository = likeSearchRepository;
    }

    /**
     * Save a like.
     *
     * @param like the entity to save
     * @return the persisted entity
     */
    @Override
    public Like save(Like like) {
        log.debug("Request to save Like : {}", like);
        Like result = likeRepository.save(like);
        likeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the likes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Like> findAll() {
        log.debug("Request to get all Likes");
        return likeRepository.findAll();
    }

    /**
     * Get one like by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Like findOne(Long id) {
        log.debug("Request to get Like : {}", id);
        return likeRepository.findOne(id);
    }

    /**
     * Delete the like by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Like : {}", id);
        likeRepository.delete(id);
        likeSearchRepository.delete(id);
    }

    /**
     * Search for the like corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Like> search(String query) {
        log.debug("Request to search Likes for query {}", query);
        return StreamSupport
                .stream(likeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
