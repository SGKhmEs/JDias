package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PollParticipationService;
import com.sgkhmjaes.jdias.domain.PollParticipation;
import com.sgkhmjaes.jdias.repository.PollParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.PollParticipationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PollParticipation.
 */
@Service
@Transactional
public class PollParticipationServiceImpl implements PollParticipationService {

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
     * Get all the pollParticipations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PollParticipation> findAll() {
        log.debug("Request to get all PollParticipations");
        return pollParticipationRepository.findAll();
    }

    /**
     * Get one pollParticipation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PollParticipation findOne(Long id) {
        log.debug("Request to get PollParticipation : {}", id);
        return pollParticipationRepository.findOne(id);
    }

    /**
     * Delete the pollParticipation by id.
     *
     * @param id the id of the entity
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
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PollParticipation> search(String query) {
        log.debug("Request to search PollParticipations for query {}", query);
        return StreamSupport
                .stream(pollParticipationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
