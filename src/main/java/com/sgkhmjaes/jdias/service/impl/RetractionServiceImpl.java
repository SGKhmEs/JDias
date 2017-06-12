package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.RetractionService;
import com.sgkhmjaes.jdias.domain.Retraction;
import com.sgkhmjaes.jdias.repository.RetractionRepository;
import com.sgkhmjaes.jdias.repository.search.RetractionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Retraction.
 */
@Service
@Transactional
public class RetractionServiceImpl implements RetractionService {

    private final Logger log = LoggerFactory.getLogger(RetractionServiceImpl.class);

    private final RetractionRepository retractionRepository;

    private final RetractionSearchRepository retractionSearchRepository;

    public RetractionServiceImpl(RetractionRepository retractionRepository, RetractionSearchRepository retractionSearchRepository) {
        this.retractionRepository = retractionRepository;
        this.retractionSearchRepository = retractionSearchRepository;
    }

    /**
     * Save a retraction.
     *
     * @param retraction the entity to save
     * @return the persisted entity
     */
    @Override
    public Retraction save(Retraction retraction) {
        log.debug("Request to save Retraction : {}", retraction);
        Retraction result = retractionRepository.save(retraction);
        retractionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the retractions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Retraction> findAll() {
        log.debug("Request to get all Retractions");
        return retractionRepository.findAll();
    }

    /**
     * Get one retraction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Retraction findOne(Long id) {
        log.debug("Request to get Retraction : {}", id);
        return retractionRepository.findOne(id);
    }

    /**
     * Delete the retraction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Retraction : {}", id);
        retractionRepository.delete(id);
        retractionSearchRepository.delete(id);
    }

    /**
     * Search for the retraction corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Retraction> search(String query) {
        log.debug("Request to search Retractions for query {}", query);
        return StreamSupport
                .stream(retractionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
