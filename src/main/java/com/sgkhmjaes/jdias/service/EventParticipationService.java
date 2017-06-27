package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.EventParticipation;
import java.util.List;

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
     *  @return the list of entities
     */
    List<EventParticipation> findAll();

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
     *  @return the list of entities
     */
    List<EventParticipation> search(String query);
}
