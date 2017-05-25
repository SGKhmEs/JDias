package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Poll> findAll(Pageable pageable);

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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Poll> search(String query, Pageable pageable);
}
