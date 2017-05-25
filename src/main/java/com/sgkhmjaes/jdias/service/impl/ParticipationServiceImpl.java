package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.ParticipationService;
import com.sgkhmjaes.jdias.domain.Participation;
import com.sgkhmjaes.jdias.repository.ParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.ParticipationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Participation.
 */
@Service
@Transactional
public class ParticipationServiceImpl implements ParticipationService{

    private final Logger log = LoggerFactory.getLogger(ParticipationServiceImpl.class);
    
    private final ParticipationRepository participationRepository;

    private final ParticipationSearchRepository participationSearchRepository;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, ParticipationSearchRepository participationSearchRepository) {
        this.participationRepository = participationRepository;
        this.participationSearchRepository = participationSearchRepository;
    }

    /**
     * Save a participation.
     *
     * @param participation the entity to save
     * @return the persisted entity
     */
    @Override
    public Participation save(Participation participation) {
        log.debug("Request to save Participation : {}", participation);
        Participation result = participationRepository.save(participation);
        participationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the participations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Participation> findAll(Pageable pageable) {
        log.debug("Request to get all Participations");
        Page<Participation> result = participationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one participation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Participation findOne(Long id) {
        log.debug("Request to get Participation : {}", id);
        Participation participation = participationRepository.findOne(id);
        return participation;
    }

    /**
     *  Delete the  participation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participation : {}", id);
        participationRepository.delete(id);
        participationSearchRepository.delete(id);
    }

    /**
     * Search for the participation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Participation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Participations for query {}", query);
        Page<Participation> result = participationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
