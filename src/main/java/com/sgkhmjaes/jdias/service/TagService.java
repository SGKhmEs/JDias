package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.domain.Tag;
import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing Tag.
 */
public interface TagService {
    
    /**
     *
     * @param statusMessage
     * @return
     */
    Set <Tag> saveAllTagsFromStatusMessages (StatusMessage statusMessage);
    
    /**
     * Save a tag.
     *
     * @param tagContext
     * @return the persisted entity
     */
    Set <Post> findPostsByTag (String tagContext);
    
    /**
     *
     * @param tagContext
     * @return
     */
    Set <Person> findPersonByTag (String tagContext);
    
    /**
     *
     * @return
     */
    List<Tag> findAll();
    
    /**
     * Search for the tag corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Tag> search(String query);
}
