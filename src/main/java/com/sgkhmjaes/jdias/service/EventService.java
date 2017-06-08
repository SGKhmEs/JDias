package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Event;
import java.util.List;

/**
 * Service Interface for managing Event.
 */
public interface EventService {

    /**
     * Save a event.
     *
     * @param event the entity to save
     * @return the persisted entity
     */
    Event save(Event event);

    /**
     *  Get all the events.
     *
     *  @return the list of entities
     */
    List<Event> findAll();

    /**
     *  Get the "id" event.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Event findOne(Long id);

    /**
     *  Delete the "id" event.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the event corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Event> search(String query);
}
