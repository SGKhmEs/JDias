package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Like.
 */
public interface LikeService {

    /**
     * Save a like.
     *
     * @param like the entity to save
     * @return the persisted entity
     */
    Like save(Like like);

    /**
     *  Get all the likes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Like> findAll(Pageable pageable);

    /**
     *  Get the "id" like.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Like findOne(Long id);

    /**
     *  Delete the "id" like.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the like corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Like> search(String query, Pageable pageable);
}
