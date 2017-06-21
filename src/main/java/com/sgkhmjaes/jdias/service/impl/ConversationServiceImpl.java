package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Conversation.
 */
@Service
@Transactional
public class ConversationServiceImpl implements ConversationService{

    private final Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);

    private final ConversationRepository conversationRepository;

    private final ConversationSearchRepository conversationSearchRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository, ConversationSearchRepository conversationSearchRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationSearchRepository = conversationSearchRepository;
    }

    /**
     * Save a conversation.
     *
     * @param conversation the entity to save
     * @return the persisted entity
     */
    @Override
    public Conversation save(Conversation conversation) {
        log.debug("Request to save Conversation : {}", conversation);
        Conversation result = conversationRepository.save(conversation);
        conversationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the conversations.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Conversation> findAll() {
        log.debug("Request to get all Conversations");
        return conversationRepository.findAllWithEagerRelationships();
    }

    /**
     *  Get one conversation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Conversation findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        return conversationRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  conversation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);
        conversationRepository.delete(id);
        conversationSearchRepository.delete(id);
    }

    /**
     * Search for the conversation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Conversation> search(String query) {
        log.debug("Request to search Conversations for query {}", query);
        return StreamSupport
            .stream(conversationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}