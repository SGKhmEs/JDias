package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Tagging;
import java.util.List;

/**
 * Service Interface for managing Tagging.
 */
public interface TaggingService {

    /**
     * Save a tagging.
     *
     * @param tagging the entity to save
     * @return the persisted entity
     */
    Tagging save(Tagging tagging);

    /**
     * Get all the taggings.
     *
     * @return the list of entities
     */
    List<Tagging> findAll();

    /**
     * Get the "id" tagging.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Tagging findOne(Long id);

    /**
     * Delete the "id" tagging.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tagging corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Tagging> search(String query);
}
