package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.Reshare;
import java.util.List;

/**
 * Service Interface for managing Reshare.
 */
public interface ReshareService {

    /**
     * Save a reshare.
     *
     * @param reshare the entity to save
     * @return the persisted entity
     */
    Reshare save(Reshare reshare);

    Reshare save(Post parrentPost);
    /**
     * Get all the reshares.
     *
     * @return the list of entities
     */
    List<Reshare> findAll();

    /**
     * Get the "id" reshare.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Reshare findOne(Long id);

    /**
     * Delete the "id" reshare.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reshare corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Reshare> search(String query);
}
