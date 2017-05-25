package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.PollAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     *  Get all the pollAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PollAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" pollAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PollAnswer findOne(Long id);

    /**
     *  Delete the "id" pollAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pollAnswer corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PollAnswer> search(String query, Pageable pageable);
}
