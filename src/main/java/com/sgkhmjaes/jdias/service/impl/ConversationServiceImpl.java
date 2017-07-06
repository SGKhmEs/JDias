
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.elasticsearch.index.query.QueryBuilders.*;
import java.util.Set;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.hibernate.Hibernate;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService{
    
    private final Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);
    private final ConversationRepository conversationRepository;
    private final ConversationSearchRepository conversationSearchRepository;
    private final UserService userService;
    private final PersonService personService;    
    private final MessageSearchRepository messageSearchRepository;
    private final MessageRepository messageRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository, ConversationSearchRepository conversationSearchRepository, 
            UserService userService,  PersonService personService, MessageSearchRepository messageSearchRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationSearchRepository = conversationSearchRepository;
        this.userService = userService;
        this.personService = personService;
        this.messageSearchRepository = messageSearchRepository;
        this.messageRepository = messageRepository;
    }
    
    @Override
    public Conversation save(Conversation conversation) {
        return save (conversation, null, userService.getCurrentPerson ());
    }
    
    @Override
    public Conversation save(Conversation conversation, Message message, Person currentPerson) {
        log.debug("Request to save Conversation : {}", conversation);
        //if (conversation.getAuthor() == null) conversation = new Conversation (currentPerson, conversation);
        if (conversation == null || conversation.getId() == null) conversation = new Conversation (currentPerson, conversation);
        else{
            Conversation findConversation = conversationRepository.findOne(conversation.getId());
            if (!findConversation.getParticipants().contains(currentPerson)) return new Conversation();
            //chenge subject of conversation
            if (conversation.getSubject() != null && !conversation.getSubject().isEmpty())findConversation.setSubject(conversation.getSubject());
            findConversation.setUpdatedAt(ZonedDateTime.now());
            findConversation.addAllParticipants (conversation.getParticipants());
            conversation = findConversation;
        }
        //set first message in conversation
        if (conversation.getMessage() == null && message != null) conversation.setMessage(message.getText());
        
        Conversation result = conversationRepository.save(conversation);        
        conversationSearchRepository.save(result);
        for (Person person: new ArrayList<>(result.getParticipants())) if (person.addUniqueConversation(result)) personService.save(person);
        return result;
    }
    
    @Override
    public List<Conversation> findAll() {
        log.debug("Request to get all Conversations");
        List<Conversation> conversations = userService.getCurrentPerson().getConversations();
        Collections.sort(conversations, (Conversation c1, Conversation c2) -> c2.getUpdatedAt().compareTo(c1.getUpdatedAt()));
        conversations.forEach((conversation) -> {Hibernate.initialize(conversation.getParticipants());});
        return conversations;
    }
    
    @Override
    public Conversation findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        Conversation conversation = conversationRepository.findOne(id);
        if (conversation.getParticipants().contains(userService.getCurrentPerson ()))return conversationRepository.findOne(id);
        else return new Conversation();
    }
    
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);
        Conversation conversation = conversationRepository.findOne(id);
        Set<Person> participants = conversation.getParticipants();
        Person currentPerson = userService.getCurrentPerson ();
        if (participants.remove(currentPerson)){
            currentPerson.getConversations().remove(conversation);
            personService.save(currentPerson);
        }
        if (participants.isEmpty()){
            for (Message message : conversation.getMessages()) {
                messageRepository.delete(message.getId());
                messageSearchRepository.delete(message.getId());
            }
            conversationRepository.delete(id);
            conversationSearchRepository.delete(id);
        }
    }
    
    @Override
    public List<Conversation> search(String query) {
        log.debug("Request to search Conversations for query {}", query);
        return StreamSupport
            .stream(conversationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }  
  
}
