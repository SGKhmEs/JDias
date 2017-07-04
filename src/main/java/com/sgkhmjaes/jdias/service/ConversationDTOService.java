
package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.dto.ConversationDTO;
import java.util.List;

/**
 *
 * @author Админ
 */
public interface ConversationDTOService {

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save
     * @return the persisted entity
     */
    ConversationDTO save(ConversationDTO conversationDTO);

    /**
     *  Get all the conversationDTOs.
     *
     *  @return the list of entities
     */
    List<ConversationDTO> findAll();

    /**
     *  Get the "id" conversationDTO.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConversationDTO findOne(Long id);

    /**
     *  Delete the "id" conversationDTO.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the conversationDTO corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ConversationDTO> search(String query);
}
