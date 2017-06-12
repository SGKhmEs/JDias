package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Like;
import java.util.List;

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
     * Get all the likes.
     *
     * @return the list of entities
     */
    List<Like> findAll();

    /**
     * Get the "id" like.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Like findOne(Long id);

    /**
     * Delete the "id" like.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the like corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Like> search(String query);
}
