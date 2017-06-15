package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Comment;
import com.sgkhmjaes.jdias.repository.CommentRepository;
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

    public CommentDTOServiceImpl(CommentRepository commentRepository, AuthorDTOServiceImpl authorDTOServiceImpl) {
        this.commentRepository = commentRepository;
        this.authorDTOServiceImpl = authorDTOServiceImpl;
    }

    public CommentDTO findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        Comment comment = commentRepository.getOne(id);
        return createCommentDTOfromComment (comment);
    }
    
    public List<CommentDTO> findAllByPost(Long id) {
        log.debug("Request to get Comments by post : {}", id);
        List<Comment> commentList = commentRepository.findByPostId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentList.forEach((comment) -> {commentDTOList.add(createCommentDTOfromComment (comment));});
        return commentDTOList;
    }
    
    private CommentDTO createCommentDTOfromComment (Comment comment) {
        AuthorDTO authorDTO = authorDTOServiceImpl.findOne(comment.getPerson().getId());
        CommentDTO commentDTO = new CommentDTO();
        try {
            commentDTO.mappingToDTO(comment, authorDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(CommentDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentDTO;
    }
    
}
