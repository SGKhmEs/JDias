package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.TagFollowing;
import java.util.List;

/**
 * Service Interface for managing TagFollowing.
 */
public interface TagFollowingService {

    /**
     * Save a tagFollowing.
     *
     * @param tagFollowing the entity to save
     * @return the persisted entity
     */
    TagFollowing save(TagFollowing tagFollowing);

    /**
     *  Get all the tagFollowings.
     *
     *  @return the list of entities
     */
    List<TagFollowing> findAll();

    /**
     *  Get the "id" tagFollowing.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TagFollowing findOne(Long id);

    /**
     *  Delete the "id" tagFollowing.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tagFollowing corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<TagFollowing> search(String query);
}
