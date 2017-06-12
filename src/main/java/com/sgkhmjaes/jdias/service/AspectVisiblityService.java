package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import java.util.List;

/**
 * Service Interface for managing AspectVisiblity.
 */
public interface AspectVisiblityService {

    /**
     * Save a aspectVisiblity.
     *
     * @param aspectVisiblity the entity to save
     * @return the persisted entity
     */
    AspectVisiblity save(AspectVisiblity aspectVisiblity);

    /**
     * Get all the aspectVisiblities.
     *
     * @return the list of entities
     */
    List<AspectVisiblity> findAll();

    /**
     * Get the "id" aspectVisiblity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AspectVisiblity findOne(Long id);

    /**
     * Delete the "id" aspectVisiblity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aspectVisiblity corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<AspectVisiblity> search(String query);
}
