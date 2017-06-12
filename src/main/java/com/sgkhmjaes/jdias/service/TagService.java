package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Tag;
import java.util.List;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tag the entity to save
     * @return the persisted entity
     */
    Tag save(Tag tag);

    /**
     * Get all the tags.
     *
     * @return the list of entities
     */
    List<Tag> findAll();

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Tag findOne(Long id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tag corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Tag> search(String query);
}
