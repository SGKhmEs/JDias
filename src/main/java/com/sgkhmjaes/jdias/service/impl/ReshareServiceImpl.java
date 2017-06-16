package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.service.ReshareService;
import com.sgkhmjaes.jdias.domain.Reshare;
import com.sgkhmjaes.jdias.repository.ReshareRepository;
import com.sgkhmjaes.jdias.repository.search.ReshareSearchRepository;
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
 * Service Implementation for managing Reshare.
 */
@Service
@Transactional
public class ReshareServiceImpl implements ReshareService {

    private final Logger log = LoggerFactory.getLogger(ReshareServiceImpl.class);

    private final ReshareRepository reshareRepository;

    private final ReshareSearchRepository reshareSearchRepository;

    private final PersonRepository personRepository;

    private final PostService postService;
    private final UserRepository userRepository;

    public ReshareServiceImpl(ReshareRepository reshareRepository, ReshareSearchRepository reshareSearchRepository, PersonRepository personRepository, PostService postService, UserRepository userRepository) {
        this.reshareRepository = reshareRepository;
        this.reshareSearchRepository = reshareSearchRepository;
        this.personRepository = personRepository;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    /**
     * Save a reshare.
     *
     * @param reshare the entity to save
     * @return the persisted entity
     */
    @Override
    public Reshare save(Reshare reshare) {
        log.debug("Request to save Reshare : {}", reshare);
        Reshare result = reshareRepository.save(reshare);
        reshareSearchRepository.save(result);
        return result;
    }

    @Override
    public Reshare save(Post parrentPost) {
        Person person = personRepository.findOne(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId());
        Reshare rsh = reshareRepository.findOne(parrentPost.getReshare().getId());
        Set<Post> posts = rsh.getPosts();
        boolean isHasRepost = false;
        for (Post p : posts) {
            if (p.getAuthor().equals(person.getDiasporaId())) {
                isHasRepost = true;
                break;
            }
        }
        if (parrentPost.getAuthor().equals(person.getDiasporaId())) {
            return parrentPost.getReshare();
        } else if (isHasRepost) {
            return parrentPost.getReshare();
        } else {
            Post post = postService.save(new Post(person.getDiasporaId(), UUID.randomUUID().toString(),
                LocalDate.now(), true, PostType.RESHARE, parrentPost.getStatusMessage(), parrentPost.getReshare(), person));
            Reshare reshare = parrentPost.getReshare();
            reshare.addPost(post);
            return save(reshare);
        }
    }

    /**
     * Get all the reshares.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reshare> findAll() {
        log.debug("Request to get all Reshares");
        return reshareRepository.findAll();
    }

    /**
     * Get one reshare by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Reshare findOne(Long id) {
        log.debug("Request to get Reshare : {}", id);
        return reshareRepository.findOne(id);
    }

    /**
     * Delete the reshare by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reshare : {}", id);
        reshareRepository.delete(id);
        reshareSearchRepository.delete(id);
    }

    /**
     * Search for the reshare corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reshare> search(String query) {
        log.debug("Request to search Reshares for query {}", query);
        return StreamSupport
                .stream(reshareSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }
}
