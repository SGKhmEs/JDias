package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.EventParticipationService;
import com.sgkhmjaes.jdias.domain.EventParticipation;
import com.sgkhmjaes.jdias.repository.EventParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.EventParticipationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EventParticipation.
 */
@Service
@Transactional
public class EventParticipationServiceImpl implements EventParticipationService {

    private final Logger log = LoggerFactory.getLogger(EventParticipationServiceImpl.class);

    private final EventParticipationRepository eventParticipationRepository;

    private final EventParticipationSearchRepository eventParticipationSearchRepository;

    public EventParticipationServiceImpl(EventParticipationRepository eventParticipationRepository, EventParticipationSearchRepository eventParticipationSearchRepository) {
        this.eventParticipationRepository = eventParticipationRepository;
        this.eventParticipationSearchRepository = eventParticipationSearchRepository;
    }

    /**
     * Save a eventParticipation.
     *
     * @param eventParticipation the entity to save
     * @return the persisted entity
     */
    @Override
    public EventParticipation save(EventParticipation eventParticipation) {
        log.debug("Request to save EventParticipation : {}", eventParticipation);
        EventParticipation result = eventParticipationRepository.save(eventParticipation);
        eventParticipationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the eventParticipations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventParticipation> findAll() {
        log.debug("Request to get all EventParticipations");
        return eventParticipationRepository.findAll();
    }

    /**
     * Get one eventParticipation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventParticipation findOne(Long id) {
        log.debug("Request to get EventParticipation : {}", id);
        return eventParticipationRepository.findOne(id);
    }

    /**
     * Delete the eventParticipation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventParticipation : {}", id);
        eventParticipationRepository.delete(id);
        eventParticipationSearchRepository.delete(id);
    }

    /**
     * Search for the eventParticipation corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventParticipation> search(String query) {
        log.debug("Request to search EventParticipations for query {}", query);
        return StreamSupport
                .stream(eventParticipationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
