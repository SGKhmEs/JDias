package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.dto.StatusMessageDTO;

import java.util.List;

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
     * Get all the statusMessages.
     *
     * @return the list of entities
     */
    List<StatusMessage> findAll();

    /**
     * Get the "id" statusMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StatusMessage findOne(Long id);

    /**
     * Delete the "id" statusMessage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void deletePost(Long id);

    /**
     * Search for the statusMessage corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<StatusMessage> search(String query);
}
