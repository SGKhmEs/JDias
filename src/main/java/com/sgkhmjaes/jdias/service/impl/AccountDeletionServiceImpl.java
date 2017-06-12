package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.AccountDeletionService;
import com.sgkhmjaes.jdias.domain.AccountDeletion;
import com.sgkhmjaes.jdias.repository.AccountDeletionRepository;
import com.sgkhmjaes.jdias.repository.search.AccountDeletionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AccountDeletion.
 */
@Service
@Transactional
public class AccountDeletionServiceImpl implements AccountDeletionService {

    private final Logger log = LoggerFactory.getLogger(AccountDeletionServiceImpl.class);

    private final AccountDeletionRepository accountDeletionRepository;

    private final AccountDeletionSearchRepository accountDeletionSearchRepository;

    public AccountDeletionServiceImpl(AccountDeletionRepository accountDeletionRepository, AccountDeletionSearchRepository accountDeletionSearchRepository) {
        this.accountDeletionRepository = accountDeletionRepository;
        this.accountDeletionSearchRepository = accountDeletionSearchRepository;
    }

    /**
     * Save a accountDeletion.
     *
     * @param accountDeletion the entity to save
     * @return the persisted entity
     */
    @Override
    public AccountDeletion save(AccountDeletion accountDeletion) {
        log.debug("Request to save AccountDeletion : {}", accountDeletion);
        AccountDeletion result = accountDeletionRepository.save(accountDeletion);
        accountDeletionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the accountDeletions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountDeletion> findAll() {
        log.debug("Request to get all AccountDeletions");
        return accountDeletionRepository.findAll();
    }

    /**
     * Get one accountDeletion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDeletion findOne(Long id) {
        log.debug("Request to get AccountDeletion : {}", id);
        return accountDeletionRepository.findOne(id);
    }

    /**
     * Delete the accountDeletion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountDeletion : {}", id);
        accountDeletionRepository.delete(id);
        accountDeletionSearchRepository.delete(id);
    }

    /**
     * Search for the accountDeletion corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountDeletion> search(String query) {
        log.debug("Request to search AccountDeletions for query {}", query);
        return StreamSupport
                .stream(accountDeletionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
