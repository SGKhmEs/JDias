package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.EventParticipationService;
import com.sgkhmjaes.jdias.domain.EventParticipation;
import com.sgkhmjaes.jdias.repository.EventParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.EventParticipationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EventParticipation.
 */
@Service
@Transactional
public class EventParticipationServiceImpl implements EventParticipationService{

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
     *  Get all the eventParticipations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventParticipation> findAll(Pageable pageable) {
        log.debug("Request to get all EventParticipations");
        Page<EventParticipation> result = eventParticipationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one eventParticipation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EventParticipation findOne(Long id) {
        log.debug("Request to get EventParticipation : {}", id);
        EventParticipation eventParticipation = eventParticipationRepository.findOne(id);
        return eventParticipation;
    }

    /**
     *  Delete the  eventParticipation by id.
     *
     *  @param id the id of the entity
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
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventParticipation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EventParticipations for query {}", query);
        Page<EventParticipation> result = eventParticipationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
