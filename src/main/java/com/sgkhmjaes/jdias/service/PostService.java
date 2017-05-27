package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Post.
 */
public interface PostService {

    /**
     * Save a post.
     *
     * @param post the entity to save
     * @return the persisted entity
     */
    Post save(Post post);

    /**
     *  Get all the posts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Post> findAll(Pageable pageable);

    /**
     *  Get the "id" post.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Post findOne(Long id);

    /**
     *  Delete the "id" post.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the post corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Post> search(String query, Pageable pageable);
}
