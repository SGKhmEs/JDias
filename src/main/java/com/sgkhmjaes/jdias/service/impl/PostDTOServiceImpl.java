package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.repository.LocationRepository;
import com.sgkhmjaes.jdias.repository.PhotoRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.InteractionDTO;
import com.sgkhmjaes.jdias.service.dto.PostDTO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostDTO findOne(Long id) {
        log.debug("Request to get Post by ID: {}", id);
        PostDTO postDTO = new PostDTO();
        try {
            Post post = postRepository.getOne(id);
            AuthorDTO authorDTO = authorDTOServiceImpl.findOne(post.getPerson().getId());
            InteractionDTO interactionDTO = interactionDTOServiceImpl.findOneByPost(id);
            postDTO.mappingToDTO(post, authorDTO, interactionDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(PostDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return postDTO;
    }

    public List<PostDTO> findAll() {
        List<Post> postList = postRepository.findAll();
        log.debug("Request to get all Posts : {}", postList.size());
        List<PostDTO> postDtoList = new ArrayList<>();
        postList.forEach((post) -> {postDtoList.add(findOne(post.getId()));});
        return postDtoList;
    }
    
}
