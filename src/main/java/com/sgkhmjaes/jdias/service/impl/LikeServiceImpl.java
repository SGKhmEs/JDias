package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.domain.enumeration.Type;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.LikeService;
import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.repository.LikeRepository;
import com.sgkhmjaes.jdias.repository.search.LikeSearchRepository;
import com.sgkhmjaes.jdias.service.UserService;
import com.sgkhmjaes.jdias.service.dto.LikeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Like.
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService{

    private final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;

    private final LikeSearchRepository likeSearchRepository;

    private final PostRepository postRepository;

    private final UserService userService;

    public LikeServiceImpl(LikeRepository likeRepository, LikeSearchRepository likeSearchRepository, PostRepository postRepository, UserService userService) {
        this.likeRepository = likeRepository;
        this.likeSearchRepository = likeSearchRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    /**
     * Save a like.
     *
     * @param like the entity to save
     * @return the persisted entity
     */
    @Override
    public Like save(Like like) {
        log.debug("Request to save Like : {}", like);
        Like result = likeRepository.save(like);
        likeSearchRepository.save(result);
        return result;
    }

    @Override
    public Like save(LikeDTO likeDTO) {
        Post post = postRepository.findOne(likeDTO.getPost_id());
        Person person = userService.getCurrentPerson();
        Like like = likeRepository.findByPersonAndPost(person, post);
        if(like == null) {
            like = save(new Like(person.getDiasporaId(), UUID.randomUUID().toString(), post.getGuid(), Type.LIKE, false, post, person));
            return like;
        }else {
            return like;
        }
    }

    /**
     *  Get all the likes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Like> findAll() {
        log.debug("Request to get all Likes");
        return likeRepository.findAll();
    }

    /**
     *  Get one like by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Like findOne(Long id) {
        log.debug("Request to get Like : {}", id);
        return likeRepository.findOne(id);
    }

    /**
     *  Delete the  like by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Like : {}", id);
        likeRepository.delete(id);
        likeSearchRepository.delete(id);
    }

    /**
     * Search for the like corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Like> search(String query) {
        log.debug("Request to search Likes for query {}", query);
        return StreamSupport
            .stream(likeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
