package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Post;
import java.util.List;
import java.util.Set;

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
     * Get all the posts.
     *
     * @return the list of entities
     */
    List<Post> findAll();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Post findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Post> search(String query);

    void deleteAll(Set<Post> postSet);
}
