package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserAccount.
 */
public interface UserAccountService {

    /**
     * Save a userAccount.
     *
     * @param userAccount the entity to save
     * @return the persisted entity
     */
    UserAccount save(UserAccount userAccount);

    /**
     *  Get all the userAccounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserAccount> findAll(Pageable pageable);

    /**
     *  Get the "id" userAccount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserAccount findOne(Long id);

    /**
     *  Delete the "id" userAccount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userAccount corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserAccount> search(String query, Pageable pageable);
}
