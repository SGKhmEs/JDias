
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
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
import java.util.HashSet;
import java.util.Set;
import java.time.ZonedDateTime;

@Service
@Transactional
public class ConversationDTOServiceImpl {
    
    private final Logger log = LoggerFactory.getLogger(ConversationDTOServiceImpl.class);
    private final ConversationRepository conversationRepository;
    private final ConversationSearchRepository conversationSearchRepository;
    private final UserRepository userRepository;
    private final AuthorDTOServiceImpl authorDTOServiceImpl;
    private final PersonRepository personRepository;
    //private final ConversationDTO conversationDTO;

    public ConversationDTOServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository, AuthorDTOServiceImpl authorDTOServiceImpl,
            PersonRepository personRepository, ConversationSearchRepository conversationSearchRepository/*, ConversationDTO conversationDTO*/) {
        this.conversationRepository = conversationRepository;
        this.conversationSearchRepository = conversationSearchRepository;
        this.userRepository=userRepository;
        this.authorDTOServiceImpl = authorDTOServiceImpl;
        this.personRepository = personRepository;
        //this.conversationDTO = conversationDTO;
    }
    
    public Conversation save(Conversation conversation) {
        log.debug("Request to save Conversation : {}", conversation);
        conversation.setCreatedAt(LocalDate.now());
        conversation.setUpdatedAt(ZonedDateTime.now()); 
        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        Person currentPerson = personRepository.getOne(currentUser.getId());
        //AuthorDTO authorDTO = authorDTOServiceImpl.findOne(currentUser.getId());
        //conversation.setAuthor(authorDTO.toString());        
        Conversation result = conversationRepository.save(conversation);
        conversationSearchRepository.save(result);
        return result;
    }

    public List<Conversation> findAll() {
        log.debug("Request to get all Conversations");
        //List<Conversation> allConversation = conversationRepository.findAll();
        /*for (Conversation conversation : allConversation) {
            conversation.
        }*/
        return conversationRepository.findAll();
    }

    public Conversation findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        //Conversation conversation = conversationRepository.findOne(id);
        //conversation.get
        return conversationRepository.findOne(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);
        conversationRepository.delete(id);
        conversationSearchRepository.delete(id);
    }

    public List<Conversation> search(String query) {
        log.debug("Request to search Conversations for query {}", query);
        return StreamSupport
            .stream(conversationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
}


