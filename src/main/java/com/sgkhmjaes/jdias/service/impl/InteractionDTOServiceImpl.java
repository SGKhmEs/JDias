
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.service.dto.InteractionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InteractionDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(InteractionDTOServiceImpl.class);
    private final CommentDTOServiceImpl commentDTOServiceImpl;

    public InteractionDTOServiceImpl(CommentDTOServiceImpl commentDTOServiceImpl) {
        this.commentDTOServiceImpl = commentDTOServiceImpl;
    }

    public InteractionDTO findOneByPost(Long id) {
        log.debug("InteractionDTOServiceImpl.findOneByPost : {id} ", id);
        InteractionDTO interactionDTO = new InteractionDTO();
        interactionDTO.setComments(commentDTOServiceImpl.findAllByPost(id));
        interactionDTO.setComments_count(interactionDTO.getComments().size());
        return interactionDTO;
    }
}
