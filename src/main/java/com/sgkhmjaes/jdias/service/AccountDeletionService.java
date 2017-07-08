package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.AccountDeletion;
import java.util.List;

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
     *  @return the list of entities
     */
    List<AccountDeletion> findAll();

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
     *  @return the list of entities
     */
    List<AccountDeletion> search(String query);
}
