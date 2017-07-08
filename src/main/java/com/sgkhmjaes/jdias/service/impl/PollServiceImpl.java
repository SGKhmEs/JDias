package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PollService;
import com.sgkhmjaes.jdias.domain.Poll;
import com.sgkhmjaes.jdias.repository.PollRepository;
import com.sgkhmjaes.jdias.repository.search.PollSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Poll.
 */
@Service
@Transactional
public class PollServiceImpl implements PollService{

    private final Logger log = LoggerFactory.getLogger(PollServiceImpl.class);

    private final PollRepository pollRepository;

    private final PollSearchRepository pollSearchRepository;

    public PollServiceImpl(PollRepository pollRepository, PollSearchRepository pollSearchRepository) {
        this.pollRepository = pollRepository;
        this.pollSearchRepository = pollSearchRepository;
    }

    /**
     * Save a poll.
     *
     * @param poll the entity to save
     * @return the persisted entity
     */
    @Override
    public Poll save(Poll poll) {
        log.debug("Request to save Poll : {}", poll);
        Poll result = pollRepository.saveAndFlush(poll);
        pollSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the polls.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Poll> findAll() {
        log.debug("Request to get all Polls");
        return pollRepository.findAll();
    }

    /**
     *  Get one poll by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Poll findOne(Long id) {
        log.debug("Request to get Poll : {}", id);
        return pollRepository.findOne(id);
    }

    /**
     *  Delete the  poll by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Poll : {}", id);
        pollRepository.delete(id);
        pollSearchRepository.delete(id);
    }

    /**
     * Search for the poll corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Poll> search(String query) {
        log.debug("Request to search Polls for query {}", query);
        return StreamSupport
            .stream(pollSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
