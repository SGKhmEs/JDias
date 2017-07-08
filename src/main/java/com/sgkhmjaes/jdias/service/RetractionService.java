package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Retraction;
import java.util.List;

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
     *  @return the list of entities
     */
    List<Retraction> findAll();

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
     *  @return the list of entities
     */
    List<Retraction> search(String query);
}
