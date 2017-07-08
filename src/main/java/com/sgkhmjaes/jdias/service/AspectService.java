package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Aspect;
import java.util.List;

/**
 * Service Interface for managing Aspect.
 */
public interface AspectService {

    /**
     * Save a aspect.
     *
     * @param aspect the entity to save
     * @return the persisted entity
     */
    Aspect save(Aspect aspect);

  /*  /**
     *  Get all the aspects.
     *
     *  @return the list of entities
     *
    List<Aspect> findAll();
    */

    /**
     *  Get all the aspects by user.
     *
     *  @return the list of entities
     */
    List<Aspect> findAllByUser();
    /**
     *  Get the "id" aspect.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Aspect findOne(Long id);

    /**
     *  Delete the "id" aspect.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aspect corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<Aspect> search(String query);
}
