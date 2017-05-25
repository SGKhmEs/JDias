package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PollParticipationService;
import com.sgkhmjaes.jdias.domain.PollParticipation;
import com.sgkhmjaes.jdias.repository.PollParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.PollParticipationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PollParticipation.
 */
@Service
@Transactional
public class PollParticipationServiceImpl implements PollParticipationService{

    private final Logger log = LoggerFactory.getLogger(PollParticipationServiceImpl.class);
    
    private final PollParticipationRepository pollParticipationRepository;

    private final PollParticipationSearchRepository pollParticipationSearchRepository;

    public PollParticipationServiceImpl(PollParticipationRepository pollParticipationRepository, PollParticipationSearchRepository pollParticipationSearchRepository) {
        this.pollParticipationRepository = pollParticipationRepository;
        this.pollParticipationSearchRepository = pollParticipationSearchRepository;
    }

    /**
     * Save a pollParticipation.
     *
     * @param pollParticipation the entity to save
     * @return the persisted entity
     */
    @Override
    public PollParticipation save(PollParticipation pollParticipation) {
        log.debug("Request to save PollParticipation : {}", pollParticipation);
        PollParticipation result = pollParticipationRepository.save(pollParticipation);
        pollParticipationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the pollParticipations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PollParticipation> findAll(Pageable pageable) {
        log.debug("Request to get all PollParticipations");
        Page<PollParticipation> result = pollParticipationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pollParticipation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PollParticipation findOne(Long id) {
        log.debug("Request to get PollParticipation : {}", id);
        PollParticipation pollParticipation = pollParticipationRepository.findOne(id);
        return pollParticipation;
    }

    /**
     *  Delete the  pollParticipation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PollParticipation : {}", id);
        pollParticipationRepository.delete(id);
        pollParticipationSearchRepository.delete(id);
    }

    /**
     * Search for the pollParticipation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PollParticipation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PollParticipations for query {}", query);
        Page<PollParticipation> result = pollParticipationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
