/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.repository.LocationRepository;
import com.sgkhmjaes.jdias.repository.PhotoRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.dto.AutoMappingException;
import com.sgkhmjaes.jdias.service.dto.PostDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author andrey
 */

@Service
@Transactional
public class PostDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(AuthorDTOServiceImpl.class);
    
    private final PostRepository postRepository;
    
    private final LocationRepository locationRepository;
    
    private final AuthorDTOServiceImpl authorDTOServiceImpl;
    
    private final PhotoRepository photoRepository;
    
    private final InteractionDTOServiceImpl interactionDTOServiceImpl;

    public PostDTOServiceImpl(PostRepository postRepository, LocationRepository locationRepository, AuthorDTOServiceImpl authorDTOServiceImpl, PhotoRepository photoRepository, InteractionDTOServiceImpl interactionDTOServiceImpl) {
        this.postRepository = postRepository;
        this.locationRepository = locationRepository;
        this.authorDTOServiceImpl = authorDTOServiceImpl;
        this.photoRepository = photoRepository;
        this.interactionDTOServiceImpl = interactionDTOServiceImpl;
    }
    
    public PostDTO findOne(Long id){
        PostDTO postDTO = new PostDTO();
        try {
            postDTO.autoMapping(postRepository.getOne(id),
                    authorDTOServiceImpl.findOne(postRepository.getOne(id).getPerson().getId()));
        } catch (AutoMappingException ex) {
            java.util.logging.Logger.getLogger(PostDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        postDTO.setInteractions(interactionDTOServiceImpl.findOneByPost(id));
        return postDTO;
    }
    
    public List<PostDTO> findAll() {
        List<Post> postList = postRepository.findAll();
        log.debug("Request to get all Posts : {}", postList.size());
        List<PostDTO> postDtoList = new ArrayList<PostDTO>();
        
        for (Post post : postList) {
            PostDTO postDTO = new PostDTO();
            try {
                postDTO.autoMapping(post,
                        authorDTOServiceImpl.findOne(post.getPerson().getId()),
                        interactionDTOServiceImpl.findOneByPost(post.getId()));
            } catch (AutoMappingException ex) {
                java.util.logging.Logger.getLogger(PostDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            postDtoList.add(postDTO);
        }
        
       return postDtoList;
    }
}
