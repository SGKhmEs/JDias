package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.UserAccountService;
import com.sgkhmjaes.jdias.domain.UserAccount;
import com.sgkhmjaes.jdias.repository.UserAccountRepository;
import com.sgkhmjaes.jdias.repository.search.UserAccountSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserAccount.
 */
@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService{

    private final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserAccountRepository userAccountRepository;

    private final UserAccountSearchRepository userAccountSearchRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserAccountSearchRepository userAccountSearchRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountSearchRepository = userAccountSearchRepository;
    }

    /**
     * Save a userAccount.
     *
     * @param userAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAccount save(UserAccount userAccount) {
        log.debug("Request to save UserAccount : {}", userAccount);
        userAccount.setId(userAccount.getUser().getId());
        UserAccount result = userAccountRepository.save(userAccount);
        userAccountSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the userAccounts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> findAll() {
        log.debug("Request to get all UserAccounts");
        return userAccountRepository.findAll();
    }

    /**
     *  Get one userAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserAccount findOne(Long id) {
        log.debug("Request to get UserAccount : {}", id);
        return userAccountRepository.findOne(id);
    }

    /**
     *  Delete the  userAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAccount : {}", id);
        userAccountRepository.delete(id);
        userAccountSearchRepository.delete(id);
    }

    /**
     * Search for the userAccount corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAccount> search(String query) {
        log.debug("Request to search UserAccounts for query {}", query);
        return StreamSupport
            .stream(userAccountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
