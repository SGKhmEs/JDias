package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Comment;
import java.util.List;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param comment the entity to save
     * @return the persisted entity
     */
    Comment save(Comment comment);

    /**
     *  Get all the comments.
     *
     *  @return the list of entities
     */
    List<Comment> findAll();

    /**
     *  Get the "id" comment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Comment findOne(Long id);

    /**
     *  Delete the "id" comment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Comment> search(String query);
}
