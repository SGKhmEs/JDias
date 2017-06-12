package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Comment;
import com.sgkhmjaes.jdias.repository.CommentRepository;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.CommentDTO;
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
        Comment comment = commentRepository.getOne(id);
        AuthorDTO authorDTO = authorDTOServiceImpl.findOne(commentRepository.getOne(id).getPerson().getId());
        CommentDTO commentDTO = new CommentDTO();
        try {
            commentDTO.mappingToDTO(comment, authorDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(CommentDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentDTO;
    }

    public List<CommentDTO> findAllByPost(Long id) {

        List<Comment> commentList = commentRepository.findByPostId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentDTO commentDTO = new CommentDTO();
            AuthorDTO authorDTO = authorDTOServiceImpl.findOne(commentRepository.getOne(comment.getId()).getPerson().getId());
            try {
                commentDTO.mappingToDTO(comment, authorDTO);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(CommentDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            commentDTOList.add(commentDTO);
        }

        return commentDTOList;

    }
}
