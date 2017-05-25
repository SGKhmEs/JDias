package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Profile.
 */
public interface ProfileService {

    /**
     * Save a profile.
     *
     * @param profile the entity to save
     * @return the persisted entity
     */
    Profile save(Profile profile);

    /**
     *  Get all the profiles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Profile> findAll(Pageable pageable);

    /**
     *  Get the "id" profile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Profile findOne(Long id);

    /**
     *  Delete the "id" profile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the profile corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Profile> search(String query, Pageable pageable);
}
