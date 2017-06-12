package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Location;
import java.util.List;

/**
 * Service Interface for managing Location.
 */
public interface LocationService {

    /**
     * Save a location.
     *
     * @param location the entity to save
     * @return the persisted entity
     */
    Location save(Location location);

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    List<Location> findAll();

    /**
     * Get the "id" location.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Location findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the location corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Location> search(String query);
}
