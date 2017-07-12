
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.service.MessageService;
import com.sgkhmjaes.jdias.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final MessageSearchRepository messageSearchRepository;
    private final UserService userService;
    private final ConversationService conversationService;

    public MessageServiceImpl(MessageRepository messageRepository, MessageSearchRepository messageSearchRepository, 
            ConversationService conversationService, UserService userService) {
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
        this.conversationService =conversationService;
        this.userService = userService;
    }
    
    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    
    @Override
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        Person currentPerson = userService.getCurrentPerson();
        Conversation conversation = conversationService.save(message.getConversation(), message, currentPerson);
        Message result = messageRepository.save(new Message (currentPerson, conversation, message));
        messageSearchRepository.save(result);
        conversation.addMessages(result);
        conversationService.save(result.getConversation(), result, currentPerson);
        return result;
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
    public List<Message> findAll() {
        log.debug("Request to get all Messages");
        List <Message> messages = new ArrayList <> ();
        Person currentPerson = userService.getCurrentPerson();
        for (Conversation conversation : currentPerson.getConversations()) messages.addAll(conversation.getMessages());
        if (messages.isEmpty()) return null;
        Collections.sort(messages, (Message m1, Message m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
        return messages;
    }
    
    /**
     *
     * @param conversation
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Message> findAllByConversation(Conversation conversation) {
        log.debug("Request to get all Messages");
        if (conversation.getParticipants().contains(userService.getCurrentPerson())){
            List<Message> messages = conversation.getMessages();
            if (messages.isEmpty()) return null;
            //Hibernate.initialize(messages);
            Collections.sort(messages, (Message m1, Message m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
            return messages;
        }
        else return new ArrayList <>();
    }

    /**
     *  Get one message by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    @Override
    public Message findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        if (message != null && message.getConversation().getParticipants().contains(userService.getCurrentPerson()))return message;
        else return new Message();
    }

    /**
     *  Delete the  message by id.
     *
     *  @param id the id of the entity
     */
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        Person currentPerson = userService.getCurrentPerson();
        Message findMessage = messageRepository.findOne(id);
        if (currentPerson.getId().equals(findMessage.getPerson().getId())){
            Conversation conversation = findMessage.getConversation();
            conversation.getMessages().remove(findMessage);            
            messageRepository.delete(id);
            messageSearchRepository.delete(id);
            conversationService.save(conversation, null, currentPerson);
        }
    }
    
    /**
     * Search for the message corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    @Override
    public List<Message> search(String query) {
        log.debug("Request to search Messages for query {}", query);
        return StreamSupport
            .stream(messageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
        
}