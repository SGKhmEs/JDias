package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Retraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Retraction.
 */
public interface RetractionService {

    /**
     * Save a retraction.
     *
     * @param retraction the entity to save
     * @return the persisted entity
     */
    Retraction save(Retraction retraction);

    /**
     *  Get all the retractions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Retraction> findAll(Pageable pageable);

    /**
     *  Get the "id" retraction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Retraction findOne(Long id);

    /**
     *  Delete the "id" retraction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the retraction corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Retraction> search(String query, Pageable pageable);
}
