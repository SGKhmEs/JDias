package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.HashTag;
import java.util.List;

/**
 * Service Interface for managing HashTag.
 */
public interface HashTagService {

    /**
     * Save a hashTag.
     *
     * @param hashTag the entity to save
     * @return the persisted entity
     */
    HashTag save(HashTag hashTag);

    /**
     *  Get all the hashTags.
     *
     *  @return the list of entities
     */
    List<HashTag> findAll();

    /**
     *  Get the "id" hashTag.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HashTag findOne(Long id);

    /**
     *  Delete the "id" hashTag.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hashTag corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<HashTag> search(String query);
}
