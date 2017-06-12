package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.StatusMessageService;
import com.sgkhmjaes.jdias.service.dto.statusMessageDTOs.StatusMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatusMessageDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(StatusMessageServiceImpl.class);
    private final StatusMessageService statusMessageService;

    public StatusMessageDTOServiceImpl(StatusMessageService statusMessageService) {
        this.statusMessageService = statusMessageService;
    }

    public void save(StatusMessageDTO statusMessageDTO) {
        log.debug("StatusMessageDTOServiceImpl.save: {StatusMessageDTO}", statusMessageDTO);
        StatusMessage statusMessage  = statusMessageDTO.getStatusMessage();
        //statusMessageDTO.mappingFromDTO(statusMessage);
        statusMessageService.save(statusMessage);
    }
}
