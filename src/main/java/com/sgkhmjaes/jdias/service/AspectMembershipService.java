package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.AspectMembership;
import java.util.List;

/**
 * Service Interface for managing AspectMembership.
 */
public interface AspectMembershipService {

    /**
     * Save a aspectMembership.
     *
     * @param aspectMembership the entity to save
     * @return the persisted entity
     */
    AspectMembership save(AspectMembership aspectMembership);

    /**
     *  Get all the aspectMemberships.
     *
     *  @return the list of entities
     */
    List<AspectMembership> findAll();

    /**
     *  Get the "id" aspectMembership.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AspectMembership findOne(Long id);

    /**
     *  Delete the "id" aspectMembership.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aspectMembership corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AspectMembership> search(String query);
}
