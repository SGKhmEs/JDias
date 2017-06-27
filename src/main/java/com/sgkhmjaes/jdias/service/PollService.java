package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Poll;
import java.util.List;

/**
 * Service Interface for managing Poll.
 */
public interface PollService {

    /**
     * Save a poll.
     *
     * @param poll the entity to save
     * @return the persisted entity
     */
    Poll save(Poll poll);

    /**
     *  Get all the polls.
     *
     *  @return the list of entities
     */
    List<Poll> findAll();

    /**
     *  Get the "id" poll.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Poll findOne(Long id);

    /**
     *  Delete the "id" poll.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the poll corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Poll> search(String query);
}
