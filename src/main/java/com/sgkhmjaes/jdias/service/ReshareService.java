package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Reshare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     *  Get all the reshares.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Reshare> findAll(Pageable pageable);

    /**
     *  Get the "id" reshare.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Reshare findOne(Long id);

    /**
     *  Delete the "id" reshare.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reshare corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Reshare> search(String query, Pageable pageable);
}
