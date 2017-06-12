package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.repository.*;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.service.StatusMessageService;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.repository.search.StatusMessageSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StatusMessage.
 */
@Service
@Transactional
public class StatusMessageServiceImpl implements StatusMessageService{

    private final Logger log = LoggerFactory.getLogger(StatusMessageServiceImpl.class);

    private final StatusMessageRepository statusMessageRepository;

    private final StatusMessageSearchRepository statusMessageSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private PostService postService;

    public StatusMessageServiceImpl(StatusMessageRepository statusMessageRepository, StatusMessageSearchRepository statusMessageSearchRepository) {
        this.statusMessageRepository = statusMessageRepository;
        this.statusMessageSearchRepository = statusMessageSearchRepository;
    }

    /**
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusMessage save(StatusMessage statusMessage) {
        StatusMessage result;
        if (statusMessage.getId() == null) {
            log.debug("Request to save StatusMessage : {}", statusMessage);
//            if(statusMessage.getPoll().getId() == 0) statusMessage.getPoll().setId(null);
//            if(statusMessage.getLocation().getId() == 0) statusMessage.getLocation().setId(null);
            result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
            Post post = new Post();
            post.setStatusMessage(statusMessage);
            post.setId(statusMessage.getId());
            Long id = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId();
            Person person = personRepository.findOne(id);
            post.setAuthor(person.getDiasporaId());
            post.setCreatedAt(LocalDate.now());
            post.setGuid(UUID.randomUUID().toString());
            post.setPerson(person);
            post.setPostType(PostType.STATUSMESSAGE);
            post.setPub(true);
            postService.save(post);
        }else {
            log.debug("Request to save StatusMessage : {}", statusMessage);
            result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
        }
        return result;
    }

    /**
     *  Get all the statusMessages.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusMessage> findAll() {
        log.debug("Request to get all StatusMessages");
        return statusMessageRepository.findAll();
    }

    /**
     *  Get one statusMessage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatusMessage findOne(Long id) {
        log.debug("Request to get StatusMessage : {}", id);
        return statusMessageRepository.findOne(id);
    }

    /**
     *  Delete the  statusMessage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusMessage : {}", id);
        statusMessageRepository.delete(id);
        statusMessageSearchRepository.delete(id);
    }

    /**
     * Search for the statusMessage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusMessage> search(String query) {
        log.debug("Request to search StatusMessages for query {}", query);
        return StreamSupport
            .stream(statusMessageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
