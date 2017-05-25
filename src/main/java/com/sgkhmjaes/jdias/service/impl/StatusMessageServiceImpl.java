package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.StatusMessageService;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.repository.StatusMessageRepository;
import com.sgkhmjaes.jdias.repository.search.StatusMessageSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StatusMessage.
 */
@Service
@Transactional
public class StatusMessageServiceImpl implements StatusMessageService{

    private final Logger log = LoggerFactory.getLogger(StatusMessageServiceImpl.class);
    
    private final StatusMessageRepository statusMessageRepository;

    private final StatusMessageSearchRepository statusMessageSearchRepository;

    public StatusMessageServiceImpl(StatusMessageRepository statusMessageRepository, StatusMessageSearchRepository statusMessageSearchRepository) {
        this.statusMessageRepository = statusMessageRepository;
        this.statusMessageSearchRepository = statusMessageSearchRepository;
    }

    /**
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusMessage save(StatusMessage statusMessage) {
        log.debug("Request to save StatusMessage : {}", statusMessage);
        StatusMessage result = statusMessageRepository.save(statusMessage);
        statusMessageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the statusMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatusMessage> findAll(Pageable pageable) {
        log.debug("Request to get all StatusMessages");
        Page<StatusMessage> result = statusMessageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one statusMessage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatusMessage findOne(Long id) {
        log.debug("Request to get StatusMessage : {}", id);
        StatusMessage statusMessage = statusMessageRepository.findOne(id);
        return statusMessage;
    }

    /**
     *  Delete the  statusMessage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusMessage : {}", id);
        statusMessageRepository.delete(id);
        statusMessageSearchRepository.delete(id);
    }

    /**
     * Search for the statusMessage corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatusMessage> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StatusMessages for query {}", query);
        Page<StatusMessage> result = statusMessageSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
