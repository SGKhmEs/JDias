package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.service.dto.StatusMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatusMessageDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostService postService;

    public StatusMessageDTOServiceImpl(PostService postService) {
        this.postService = postService;
    }

    public void save(StatusMessageDTO statusMessageDTO) {
        log.debug("StatusMessageDTOServiceImpl.save: {StatusMessageDTO}", statusMessageDTO);
        StatusMessage statusMessage  = statusMessageDTO.getStatusMessage();
        //statusMessageDTO.mappingFromDTO(statusMessage);
        postService.save(statusMessage);
    }
}
