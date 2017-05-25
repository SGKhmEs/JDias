package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StatusMessage.
 */
public interface StatusMessageService {

    /**
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    StatusMessage save(StatusMessage statusMessage);

    /**
     *  Get all the statusMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StatusMessage> findAll(Pageable pageable);

    /**
     *  Get the "id" statusMessage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StatusMessage findOne(Long id);

    /**
     *  Delete the "id" statusMessage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the statusMessage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StatusMessage> search(String query, Pageable pageable);
}
