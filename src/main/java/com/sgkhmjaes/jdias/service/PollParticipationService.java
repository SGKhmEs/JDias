package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.PollParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PollParticipation.
 */
public interface PollParticipationService {

    /**
     * Save a pollParticipation.
     *
     * @param pollParticipation the entity to save
     * @return the persisted entity
     */
    PollParticipation save(PollParticipation pollParticipation);

    /**
     *  Get all the pollParticipations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PollParticipation> findAll(Pageable pageable);

    /**
     *  Get the "id" pollParticipation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PollParticipation findOne(Long id);

    /**
     *  Delete the "id" pollParticipation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pollParticipation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PollParticipation> search(String query, Pageable pageable);
}
