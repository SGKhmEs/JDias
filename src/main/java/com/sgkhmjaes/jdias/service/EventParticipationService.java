package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.EventParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EventParticipation.
 */
public interface EventParticipationService {

    /**
     * Save a eventParticipation.
     *
     * @param eventParticipation the entity to save
     * @return the persisted entity
     */
    EventParticipation save(EventParticipation eventParticipation);

    /**
     *  Get all the eventParticipations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EventParticipation> findAll(Pageable pageable);

    /**
     *  Get the "id" eventParticipation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EventParticipation findOne(Long id);

    /**
     *  Delete the "id" eventParticipation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the eventParticipation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EventParticipation> search(String query, Pageable pageable);
}
