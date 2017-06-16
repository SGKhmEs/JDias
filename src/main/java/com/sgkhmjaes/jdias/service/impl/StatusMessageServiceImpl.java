package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.Reshare;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.repository.*;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.service.ReshareService;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StatusMessage.
 */
@Service
@Transactional
public class StatusMessageServiceImpl implements StatusMessageService {

    private final Logger log = LoggerFactory.getLogger(StatusMessageServiceImpl.class);
    private final StatusMessageRepository statusMessageRepository;
    private final StatusMessageSearchRepository statusMessageSearchRepository;

    private final UserRepository userRepository;

    private final PersonRepository personRepository;

    private final PostService postService;
    private final ReshareService reshareService;

    public StatusMessageServiceImpl(StatusMessageRepository statusMessageRepository, StatusMessageSearchRepository statusMessageSearchRepository, UserRepository userRepository, PersonRepository personRepository, PostService postService, ReshareService reshareService) {
        this.statusMessageRepository = statusMessageRepository;
        this.statusMessageSearchRepository = statusMessageSearchRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.postService = postService;
        this.reshareService = reshareService;
    }

    /**
     * Save a statusMessage.
     *
     * @param statusMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusMessage save(StatusMessage statusMessage) {
        log.debug("Request to save StatusMessage : {}", statusMessage);
        if (statusMessage.getId() == null) {
            Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
            Post post = postService.save(new Post(person.getDiasporaId(), UUID.randomUUID().toString(),
                LocalDate.now(), true, PostType.STATUSMESSAGE, null, null, person));
            statusMessage.setId(post.getId());
            statusMessage.addPost(post);
            StatusMessage result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
            Reshare reshare = new Reshare(post.getId(), post.getAuthor(), post.getGuid());
            reshare.addPost(post);
            reshare = reshareService.save(reshare);
            post.setStatusMessage(result);
            post.setReshare(reshare);
            postService.save(post);
            return result;
        } else {
            StatusMessage result = statusMessageRepository.save(statusMessage);
            statusMessageSearchRepository.save(result);
            return result;
        }
    }

    /**
     * Get all the statusMessages.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusMessage> findAll() {
        log.debug("Request to get all StatusMessages");
        return statusMessageRepository.findAll();
    }

    /**
     * Get one statusMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatusMessage findOne(Long id) {
        log.debug("Request to get StatusMessage : {}", id);
        return statusMessageRepository.findOne(id);
    }

    /**
     * Delete the statusMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusMessage : {}", id);
        Reshare reshare = reshareService.findOne(id);
        Set<Post> postSet = reshare.getPosts();
        postService.deleteAll(postSet);
        reshareService.delete(id);
        statusMessageRepository.delete(id);
        statusMessageSearchRepository.delete(id);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postService.findOne(id);
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        StatusMessage statusMessage = findOne(post.getStatusMessage().getId());
        if (person.getDiasporaId().equals(post.getAuthor())) {
            if (post.getId().equals(statusMessage.getId())) {
                delete(id);
            }else {
                statusMessage.removePost(post);
                Reshare reshare = reshareService.findOne(statusMessage.getId());
                reshare.removePost(post);
                reshareService.save(reshare);
                save(statusMessage);
                postService.delete(id);
            }
        }
    }

    /**
     * Search for the statusMessage corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
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
