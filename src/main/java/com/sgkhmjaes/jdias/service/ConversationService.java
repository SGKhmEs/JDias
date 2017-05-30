package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Conversation.
 */
public interface ConversationService {

    /**
     * Save a conversation.
     *
     * @param conversation the entity to save
     * @return the persisted entity
     */
    Conversation save(Conversation conversation);

    /**
     *  Get all the conversations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Conversation> findAll(Pageable pageable);

    /**
     *  Get the "id" conversation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Conversation findOne(Long id);

    /**
     *  Delete the "id" conversation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the conversation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Conversation> search(String query, Pageable pageable);
}
