
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.dto.ConversationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.elasticsearch.index.query.QueryBuilders.*;
import java.time.LocalDate;
import java.util.Set;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.UUID;
import org.hibernate.Hibernate;

@Service
@Transactional
public class ConversationDTOServiceImpl {
    
    private final Logger log = LoggerFactory.getLogger(ConversationDTOServiceImpl.class);
    private final ConversationRepository conversationRepository;
    private final ConversationSearchRepository conversationSearchRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;
    private final MessageSearchRepository messageSearchRepository;

    public ConversationDTOServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository, 
            PersonRepository personRepository, ConversationSearchRepository conversationSearchRepository, 
            MessageRepository messageRepository, MessageSearchRepository messageSearchRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationSearchRepository = conversationSearchRepository;
        this.userRepository=userRepository;
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
    }
    
    public Conversation save(Conversation conversation) {
        return save (conversation, null, getCurrentPerson ());
    }
    
    public Conversation save(Conversation conversation, Message message, Person currentPerson) {
        log.debug("Request to save Conversation : {}", conversation);
        if (conversation == null || conversation.getId() == null) {
            conversation = new Conversation (currentPerson, conversation);
            if (conversation.getMessage() == null && message != null) conversation.setMessage(message.getText());
        }
        else{
            Conversation findConversation = conversationRepository.findOne(conversation.getId());
            if (!findConversation.getParticipants().contains(currentPerson)) return new Conversation();
            findConversation.setUpdatedAt(ZonedDateTime.now());
            findConversation.addAllParticipants (conversation.getParticipants());
            conversation = findConversation;
        }
        
        Hibernate.initialize(conversation);
        Conversation result = conversationRepository.save(conversation);
        conversationSearchRepository.save(result);
        return result;
    }

    public List<Conversation> findAll() {

        log.debug("Request to get all Conversations");
        List<Conversation> conversations = getCurrentPerson().getConversations();
        Collections.sort(conversations, (Conversation c1, Conversation c2) -> c2.getUpdatedAt().compareTo(c1.getUpdatedAt()));
        for (Conversation conversation : conversations) Hibernate.initialize(conversation.getParticipants());
        return conversations;
    }

    public Conversation findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        Conversation conversation = conversationRepository.findOne(id);
        if (conversation.getParticipants().contains(getCurrentPerson ()))return conversationRepository.findOne(id);
        else return new Conversation();
    }

    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);
        Conversation conversation = conversationRepository.findOne(id);
        Set<Person> participants = conversation.getParticipants();
        Person currentPerson = getCurrentPerson ();
        if (participants.remove(currentPerson)){
            currentPerson.getConversations().remove(conversation);
            personRepository.save(currentPerson);
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
    
    public List<Conversation> search(String query) {
        log.debug("Request to search Conversations for query {}", query);
        return StreamSupport
            .stream(conversationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    private Person getCurrentPerson (){
        return personRepository.getOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
    }
    
}
