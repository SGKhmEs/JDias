/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.repository.LikeRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.LikeDTO;
import com.sgkhmjaes.jdias.service.dto.LikeDTO;
import java.lang.reflect.InvocationTargetException;
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
public class LikeDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(LikeDTOServiceImpl.class);

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    private final PersonRepository personRepository;

    private final AuthorDTOServiceImpl authorDTOServiceImpl;

    public LikeDTOServiceImpl(PostRepository postRepository, LikeRepository likeRepository,
            PersonRepository personRepository, AuthorDTOServiceImpl authorDTOServiceImpl) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.personRepository = personRepository;
        this.authorDTOServiceImpl = authorDTOServiceImpl;
    }

    public List<LikeDTO> findAllByPostId(Long id) {
        List<Like> likeList = likeRepository.findaAllByPostId(id);

        List<LikeDTO> likeDTOList = new ArrayList<LikeDTO>();

        for (Like like : likeList) {
            
            LikeDTO likeDTO = new LikeDTO();
            AuthorDTO authorDTO = authorDTOServiceImpl.findOne(likeRepository.getOne(like.getId()).getPerson().getId());
            System.out.println(id + like.toString() + "    " + authorDTO.toString());

            try {
                likeDTO.mappingToDTO(like, authorDTO);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(LikeDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            likeDTOList.add(likeDTO);

        }
        return likeDTOList;
    }
}
