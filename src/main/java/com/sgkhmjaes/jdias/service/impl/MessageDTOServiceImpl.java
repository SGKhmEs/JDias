
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.service.MessageDTOService;
import com.sgkhmjaes.jdias.service.MessageService;
import com.sgkhmjaes.jdias.service.UserService;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import com.sgkhmjaes.jdias.service.dto.ConversationDTO;
import com.sgkhmjaes.jdias.service.dto.MessageDTO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.logging.Level;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageDTOServiceImpl implements MessageDTOService {
    
    private final Logger log = LoggerFactory.getLogger(MessageDTOServiceImpl.class);
    private final UserService userService;
    private final ConversationService conversationService;
    private final AvatarDTOServiceImpl avatarDTOServiceImpl;
    private final MessageService messageService;

    public MessageDTOServiceImpl (ConversationService conversationService, UserService userService, 
            AvatarDTOServiceImpl avatarDTOServiceImpl, MessageService messageService) {
        this.conversationService =conversationService;
        this.userService = userService;
        this.avatarDTOServiceImpl= avatarDTOServiceImpl;
        this.messageService = messageService;
    }
    
    /**
     * Save a message.
     *
     * @return the persisted entity
     */
    
    @Override
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save MessageDTO : {}", messageDTO);
        Person currentPerson = userService.getCurrentPerson();
        Message createMessage = createMessageFromMessageDTO(messageDTO, currentPerson);
        Message saveMessage = messageService.save(createMessage);
        return createMessageDTOFromMessage(saveMessage);
    }
    
    /**
     *  Get all the messages.
     *
     *  @return the list of entities
     */
    
    // this mrthod need for testing
    // nedd use 'findAllByConversation'
    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> findAll() {
        log.debug("Request to get all MessagesDTO");
        List <MessageDTO> messageDTOs = new ArrayList <> ();
        messageService.findAll().forEach((message) -> {messageDTOs.add(createMessageDTOFromMessage(message));});
        return messageDTOs;
    }
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> findAllByConversation(ConversationDTO conversationDTO) {
        log.debug("Request to get all MessagesDTO from ConversationDTO: " + conversationDTO);
        Conversation conversation = conversationService.findOne(conversationDTO.getId());
        if (conversation.getParticipants().contains(userService.getCurrentPerson())) {
            ArrayList <MessageDTO> messageDTOs = new ArrayList <>();
            conversation.getMessages().forEach((message) -> {messageDTOs.add(createMessageDTOFromMessage(message));});
            Collections.sort(messageDTOs, (MessageDTO m1, MessageDTO m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
            return messageDTOs;
        } else return new ArrayList <>();
    }

    /**
     *  Get one message by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    @Override
    public MessageDTO findOne(Long id) {
        log.debug("Request to get MessageDTO : {}", id);
        return createMessageDTOFromMessage(messageService.findOne(id));
    }

    /**
     *  Delete the  message by id.
     *
     *  @param id the id of the entity
     */
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageDTO : {}", id);
        messageService.delete(id);
    }
    
    /**
     * Search for the message corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> search(String query) {
        log.debug("Request to search MessageDTOs for query {}", query);
        List <MessageDTO> messageDTOs = new ArrayList <> ();
        messageService.search(query).forEach((message) -> {messageDTOs.add(createMessageDTOFromMessage(message));});
        return messageDTOs;
    }
    
    private Message createMessageFromMessageDTO (MessageDTO messageDTO, Person currentPerson){
        Message message = new Message (currentPerson);
        try {
            messageDTO.mappingFromDTO(message);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(MessageDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        message.setPerson(userService.getCurrentPerson());
        return message;
    }
    
    private MessageDTO createMessageDTOFromMessage (Message message/*, Person currentPerson*/){
        MessageDTO messageDTO = new MessageDTO ();
        AuthorDTO authorDTO = new AuthorDTO() ;
        AvatarDTO avatarDTO = avatarDTOServiceImpl.findOne(message.getPerson().getId());
        try {
            authorDTO.mappingToDTO(avatarDTO, message.getPerson().getId());
            messageDTO.mappingToDTO(message, authorDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(MessageDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messageDTO;
    }
    
}