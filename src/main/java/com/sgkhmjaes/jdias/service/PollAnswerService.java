package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.PollAnswer;
import java.util.List;

/**
 * Service Interface for managing PollAnswer.
 */
public interface PollAnswerService {

    /**
     * Save a pollAnswer.
     *
     * @param pollAnswer the entity to save
     * @return the persisted entity
     */
    PollAnswer save(PollAnswer pollAnswer);

    /**
     * Get all the pollAnswers.
     *
     * @return the list of entities
     */
    List<PollAnswer> findAll();

    /**
     * Get the "id" pollAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PollAnswer findOne(Long id);

    /**
     * Delete the "id" pollAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pollAnswer corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<PollAnswer> search(String query);
}
