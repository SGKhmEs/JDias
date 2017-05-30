package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.repository.search.PostSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Post.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService{

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostSearchRepository postSearchRepository;

    public PostServiceImpl(PostRepository postRepository, PostSearchRepository postSearchRepository) {
        this.postRepository = postRepository;
        this.postSearchRepository = postSearchRepository;
    }

    /**
     * Save a post.
     *
     * @param post the entity to save
     * @return the persisted entity
     */
    @Override
    public Post save(Post post) {
        log.debug("Request to save Post : {}", post);
        Post result = postRepository.save(post);
        postSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the posts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable);
    }

    /**
     *  Get one post by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Post findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findOne(id);
    }

    /**
     *  Delete the  post by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.delete(id);
        postSearchRepository.delete(id);
    }

    /**
     * Search for the post corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Post> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Posts for query {}", query);
        Page<Post> result = postSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
