package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Profile;
import java.util.List;

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
     *  @return the list of entities
     */
    List<Profile> findAll();

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
     *  @return the list of entities
     */
    List<Profile> search(String query);
}
