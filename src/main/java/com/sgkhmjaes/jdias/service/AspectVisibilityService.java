package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.service.dto.AspectvisibilityDTO;
import java.util.List;

/**
 * Service Interface for managing Aspectvisibility.
 */
public interface AspectvisibilityService {

    /**
     * Save a aspectvisibility.
     *
     * @param aspectvisibilityDTO the entity to save
     * @return the persisted entity
     */
    AspectvisibilityDTO save(AspectvisibilityDTO aspectvisibilityDTO);

    /**
     *  Get all the aspectvisibilities.
     *
     *  @return the list of entities
     */
    List<AspectvisibilityDTO> findAll();

    /**
     *  Get the "id" aspectvisibility.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AspectvisibilityDTO findOne(Long id);

    /**
     *  Delete the "id" aspectvisibility.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aspectvisibility corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<AspectvisibilityDTO> search(String query);
}
