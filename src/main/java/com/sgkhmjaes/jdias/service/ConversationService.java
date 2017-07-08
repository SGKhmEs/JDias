package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import java.util.List;

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
    Conversation save(Conversation conversation, Message message, Person person);

    /**
     *  Get all the conversations.
     *
     *  @return the list of entities
     */
    List<Conversation> findAll();

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
     *  @return the list of entities
     */
    List<Conversation> search(String query);
}
