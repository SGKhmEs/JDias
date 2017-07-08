
package com.sgkhmjaes.jdias.service;

import com.sgkhmjaes.jdias.service.dto.ConversationDTO;
import com.sgkhmjaes.jdias.service.dto.MessageDTO;
import java.util.List;

public interface MessageDTOService {

    /**
     * Save a messageDTO.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    MessageDTO save(MessageDTO messageDTO);

    /**
     *  Get all the messageDTOs.
     *
     *  @return the list of entities
     */
    List<MessageDTO> findAll();

    /**
     *
     * @param conversationDTO
     * @return
     */
    List<MessageDTO> findAllByConversation(ConversationDTO conversationDTO);

    /**
     *  Get the "id" messageDTO.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MessageDTO findOne(Long id);

    /**
     *  Delete the "id" messageDTO.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the messageDTO corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<MessageDTO> search(String query);
}

