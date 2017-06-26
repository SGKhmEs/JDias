
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
    private final LikeDTOServiceImpl likeDTOServiceImpl;

    public InteractionDTOServiceImpl(CommentDTOServiceImpl commentDTOServiceImpl, LikeDTOServiceImpl likeDTOServiceImpl) {
        this.commentDTOServiceImpl = commentDTOServiceImpl;
        this.likeDTOServiceImpl = likeDTOServiceImpl;
    }


    public InteractionDTO findOneByPost(Long id) {
        log.debug("InteractionDTOServiceImpl.findOneByPost : {id} ", id);
        InteractionDTO interactionDTO = new InteractionDTO();
        interactionDTO.setComments(commentDTOServiceImpl.findAllByPost(id));
        interactionDTO.setComments_count(interactionDTO.getComments().size());
        interactionDTO.setLikes(likeDTOServiceImpl.findAllByPostId(id));
        interactionDTO.setLikes_count(interactionDTO.getLikes().size());
        return interactionDTO;
    }
}
