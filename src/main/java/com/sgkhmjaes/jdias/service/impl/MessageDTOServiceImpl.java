
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.dto.MessageDTO;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.hibernate.Hibernate;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageRepository messageRepository;
    private final MessageSearchRepository messageSearchRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationDTOServiceImpl conversationDTOServiceImpl;
    //private final MessageDTO messageDTO;

    public MessageDTOServiceImpl(MessageRepository messageRepository, MessageSearchRepository messageSearchRepository, 
            UserRepository userRepository, PersonRepository personRepository, ConversationRepository conversationRepository,
            ConversationDTOServiceImpl conversationDTOServiceImpl/*, MessageDTO messageDTO*/) {
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
        this.userRepository  = userRepository;
        this.personRepository = personRepository;
        this.conversationRepository = conversationRepository;
        this.conversationDTOServiceImpl = conversationDTOServiceImpl;
        //this.messageDTO = messageDTO;
    }
    
    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        Person currentPerson = getCurrentPerson();
        message.setCreatedAt(ZonedDateTime.now());
        message.setGuid(UUID.randomUUID().toString());
        message.setAuthor(currentPerson.getDiasporaId());
        Conversation conversation = message.getConversation();
        if (conversation == null) conversation = new Conversation (message.getText());
        else conversation = conversationDTOServiceImpl.findOne(conversation.getId());
        if (message.getPerson() != null) conversation.addParticipants(message.getPerson());
        if (conversation.getMessage() == null) conversation.setMessage(message.getText());
        message.setPerson(currentPerson);
        conversation.addMessages(message);
        Hibernate.initialize(conversation.getParticipants());
        Conversation saveConversation = conversationDTOServiceImpl.save(conversation);
        message.setConversation(saveConversation);
        message.setConversationGuid(saveConversation.getGuid());
        Message result = messageRepository.save(message);
        messageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the messages.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        log.debug("Request to get all Messages");
        List <Message> messages = new ArrayList <> ();
        Person currentPerson = getCurrentPerson();
        Hibernate.initialize(currentPerson);
        for (Conversation conversation : currentPerson.getConversations()) 
            messages.addAll(conversation.getMessages());
        Collections.sort(messages, (Message m1, Message m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()));
        return messages;
    }
    
    @Transactional(readOnly = true)
    public List<Message> findAllByConversation(Conversation conversation) {
        log.debug("Request to get all Messages");
        if (conversation.getParticipants().contains(getCurrentPerson())){
            List<Message> messages = conversation.getMessages();
            Hibernate.initialize(messages);
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
    public Message findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        if (message.getConversation().getParticipants().contains(getCurrentPerson()))return message;
        else return new Message();
    }

    /**
     *  Delete the  message by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        Person currentPerson = getCurrentPerson();
        Message findMessage = messageRepository.findOne(id);
        if (currentPerson.getId().equals(findMessage.getPerson().getId())){
            Conversation conversation = findMessage.getConversation();
            conversation.getMessages().remove(findMessage);            
            messageRepository.delete(id);
            messageSearchRepository.delete(id);
            conversationRepository.save(conversation);
        }
    }

    /**
     * Search for the message corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Message> search(String query) {
        log.debug("Request to search Messages for query {}", query);
        return StreamSupport
            .stream(messageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    private Person getCurrentPerson (){
        return personRepository.getOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
    }
    
}
