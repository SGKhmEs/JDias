package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Conversation> findAll(Pageable pageable) {
        log.debug("Request to get all Conversations");
        return conversationRepository.findAll(pageable);
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
        return conversationRepository.findOne(id);
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
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Conversation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Conversations for query {}", query);
        Page<Conversation> result = conversationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
