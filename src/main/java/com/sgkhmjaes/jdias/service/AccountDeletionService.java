package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.AccountDeletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AccountDeletion.
 */
public interface AccountDeletionService {

    /**
     * Save a accountDeletion.
     *
     * @param accountDeletion the entity to save
     * @return the persisted entity
     */
    AccountDeletion save(AccountDeletion accountDeletion);

    /**
     *  Get all the accountDeletions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AccountDeletion> findAll(Pageable pageable);

    /**
     *  Get the "id" accountDeletion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AccountDeletion findOne(Long id);

    /**
     *  Delete the "id" accountDeletion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the accountDeletion corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AccountDeletion> search(String query, Pageable pageable);
}
