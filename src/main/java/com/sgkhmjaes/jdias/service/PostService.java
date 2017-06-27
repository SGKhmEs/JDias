package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.Reshare;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.dto.StatusMessageDTO;

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
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    StatusMessage save(StatusMessage statusMessage);

    /**
     * Save a statusMessage.
     *
     * @param statusMessageDTO the entity to save
     * @return the persisted entity
     */
    StatusMessage save(StatusMessageDTO statusMessageDTO);

    /**
     * Save a reshare.
     *
     * @param reshare the entity to save
     * @return the persisted entity
     */
    Reshare save(Reshare reshare);

    /**
     * Save a reshare.
     *
     * @param parrentPost the entity to reshare
     * @return the persisted entity
     */
    Reshare saveReshare(Post parrentPost);

    /**
     * Get all the posts.
     *
     * @return the list of entities
     */
    List<Post> findAllPost();

    /**
     * Get all the statusMessages.
     *
     * @return the list of entities
     */
    List<StatusMessage> findAllStatusMessage();

    /**
     * Get all the reshares.
     *
     * @return the list of entities
     */
    List<Reshare> findAllReshare();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Post findOnePost(Long id);

    /**
     * Get the "id" statusMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StatusMessage findOneStatusMessage(Long id);

    /**
     * Get the "id" reshare.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Reshare findOneReshare(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Delete the "id" statusMessage.
     *
     * @param id the id of the entity
     */
    void deleteStatusMessage(Long id);

    void deletePost(Long id);

    void deleteSetPosts(Set<Post> postSet);


    /**
     * Delete the "id" reshare.
     *
     * @param id the id of the entity
     */
    void deleteReshare(Long id);

    /**
     * Search for the post corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Post> searchPost(String query);

    /**
     * Search for the statusMessage corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<StatusMessage> searchStatusMessage(String query);


    /**
     * Search for the reshare corresponding to the query.
     *
     * @param query the query of the search
     *
     * @return the list of entities
     */
    List<Reshare> searchReshare(String query);

}
