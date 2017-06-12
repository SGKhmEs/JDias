package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.MessageService;
import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
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

    public MessageServiceImpl(MessageRepository messageRepository, MessageSearchRepository messageSearchRepository) {
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
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
        Message result = messageRepository.save(message);
        messageSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the messages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Message> findAll() {
        log.debug("Request to get all Messages");
        return messageRepository.findAll();
    }

    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Message findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findOne(id);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
        messageSearchRepository.delete(id);
    }

    /**
     * Search for the message corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Message> search(String query) {
        log.debug("Request to search Messages for query {}", query);
        return StreamSupport
                .stream(messageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
