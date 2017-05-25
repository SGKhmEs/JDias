package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PollAnswerService;
import com.sgkhmjaes.jdias.domain.PollAnswer;
import com.sgkhmjaes.jdias.repository.PollAnswerRepository;
import com.sgkhmjaes.jdias.repository.search.PollAnswerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PollAnswer.
 */
@Service
@Transactional
public class PollAnswerServiceImpl implements PollAnswerService{

    private final Logger log = LoggerFactory.getLogger(PollAnswerServiceImpl.class);
    
    private final PollAnswerRepository pollAnswerRepository;

    private final PollAnswerSearchRepository pollAnswerSearchRepository;

    public PollAnswerServiceImpl(PollAnswerRepository pollAnswerRepository, PollAnswerSearchRepository pollAnswerSearchRepository) {
        this.pollAnswerRepository = pollAnswerRepository;
        this.pollAnswerSearchRepository = pollAnswerSearchRepository;
    }

    /**
     * Save a pollAnswer.
     *
     * @param pollAnswer the entity to save
     * @return the persisted entity
     */
    @Override
    public PollAnswer save(PollAnswer pollAnswer) {
        log.debug("Request to save PollAnswer : {}", pollAnswer);
        PollAnswer result = pollAnswerRepository.save(pollAnswer);
        pollAnswerSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the pollAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PollAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all PollAnswers");
        Page<PollAnswer> result = pollAnswerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pollAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PollAnswer findOne(Long id) {
        log.debug("Request to get PollAnswer : {}", id);
        PollAnswer pollAnswer = pollAnswerRepository.findOne(id);
        return pollAnswer;
    }

    /**
     *  Delete the  pollAnswer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PollAnswer : {}", id);
        pollAnswerRepository.delete(id);
        pollAnswerSearchRepository.delete(id);
    }

    /**
     * Search for the pollAnswer corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PollAnswer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PollAnswers for query {}", query);
        Page<PollAnswer> result = pollAnswerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
