package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Participation.
 */
public interface ParticipationService {

    /**
     * Save a participation.
     *
     * @param participation the entity to save
     * @return the persisted entity
     */
    Participation save(Participation participation);

    /**
     *  Get all the participations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Participation> findAll(Pageable pageable);

    /**
     *  Get the "id" participation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Participation findOne(Long id);

    /**
     *  Delete the "id" participation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the participation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Participation> search(String query, Pageable pageable);
}
