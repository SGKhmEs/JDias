package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.PollParticipation;
import java.util.List;

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
     * Get all the pollParticipations.
     *
     * @return the list of entities
     */
    List<PollParticipation> findAll();

    /**
     * Get the "id" pollParticipation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PollParticipation findOne(Long id);

    /**
     * Delete the "id" pollParticipation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pollParticipation corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<PollParticipation> search(String query);
}
