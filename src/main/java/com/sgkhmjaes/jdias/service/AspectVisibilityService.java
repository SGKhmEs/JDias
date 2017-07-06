package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;
import java.util.List;

/**
 * Service Interface for managing AspectVisibility.
 */
public interface AspectVisibilityService {

    /**
     * Save a aspectVisibility.
     *
     * @param aspectVisibilityDTO the entity to save
     * @return the persisted entity
     */
    AspectVisibilityDTO save(AspectVisibilityDTO aspectVisibilityDTO);

    /**
     *  Get all the aspectVisibilities.
     *
     *  @return the list of entities
     */
    List<AspectVisibilityDTO> findAll();

    /**
     *  Get the "id" aspectVisibility.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AspectVisibilityDTO findOne(Long id);

    /**
     *  Delete the "id" aspectVisibility.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aspectVisibility corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AspectVisibilityDTO> search(String query);
}
