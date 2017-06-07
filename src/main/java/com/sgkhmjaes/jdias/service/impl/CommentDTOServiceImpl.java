/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Comment;
import com.sgkhmjaes.jdias.repository.CommentRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.dto.AutoMappingException;
import com.sgkhmjaes.jdias.service.dto.CommentDTO;
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
public class CommentDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(CommentDTOServiceImpl.class);
    
    private final CommentRepository commentRepository;
    
    private final AuthorDTOServiceImpl authorDTOServiceImpl;
    
    private final PostRepository postRepository;

    public CommentDTOServiceImpl(CommentRepository commentRepository, AuthorDTOServiceImpl authorDTOServiceImpl, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.authorDTOServiceImpl = authorDTOServiceImpl;
        this.postRepository = postRepository;
    }

    public CommentDTO findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        
        CommentDTO commentDTO = new CommentDTO();
        try {
            commentDTO.autoMapping(commentRepository.getOne(id));
        } catch (AutoMappingException ex) {
            java.util.logging.Logger.getLogger(CommentDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        commentDTO.setAuthor(authorDTOServiceImpl.findOne(commentRepository.getOne(id).getPerson().getId()));
        return commentDTO;
    }

    public List<CommentDTO> findAllByPost(Long id){
        List<Comment> commentList = commentRepository.findByPostId(id);
        
        List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
        
        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO();
            try {
                commentDTO.autoMapping(comment);
            } catch (AutoMappingException ex) {
                java.util.logging.Logger.getLogger(CommentDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            commentDTO.setAuthor(authorDTOServiceImpl.findOne(comment.getPerson().getId()));
        commentDTOList.add(commentDTO);
        }
        
        return commentDTOList;
        
       
    }    
}
