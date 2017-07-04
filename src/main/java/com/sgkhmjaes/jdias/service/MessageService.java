package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import java.util.List;

/**
 * Service Interface for managing Message.
 */
public interface MessageService {

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    Message save(Message message);

    /**
     *  Get all the messages.
     *
     *  @return the list of entities
     */
    List<Message> findAll();

    /**
     *
     * @param conversation
     * @return
     */
    List<Message> findAllByConversation(Conversation conversation);

    /**
     *  Get the "id" message.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Message findOne(Long id);

    /**
     *  Delete the "id" message.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the message corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Message> search(String query);
}
