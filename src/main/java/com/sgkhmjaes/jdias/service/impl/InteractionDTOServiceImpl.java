/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.dto.InteractionDTO;
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
public class InteractionDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(InteractionDTOServiceImpl.class);
    
    private final CommentDTOServiceImpl commentDTOServiceImpl;

    public InteractionDTOServiceImpl(CommentDTOServiceImpl commentDTOServiceImpl) {
        this.commentDTOServiceImpl = commentDTOServiceImpl;
    }
    
    public InteractionDTO findOneByPost(Long id) {
        InteractionDTO interactionDTO = new InteractionDTO();
        interactionDTO.setComments(commentDTOServiceImpl.findAllByPost(id));
        interactionDTO.setComments_count(commentDTOServiceImpl.findAllByPost(id).size());
        return interactionDTO;
    }
}
