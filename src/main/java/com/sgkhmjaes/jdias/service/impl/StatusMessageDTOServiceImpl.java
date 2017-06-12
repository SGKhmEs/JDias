package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.service.StatusMessageService;
import com.sgkhmjaes.jdias.service.dto.statusMessageDTOs.StatusMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by inna on 10.06.17.
 */
@Service
@Transactional
public class StatusMessageDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(StatusMessageServiceImpl.class);

    private final StatusMessageService statusMessageService;

    public StatusMessageDTOServiceImpl(StatusMessageService statusMessageService) {
        this.statusMessageService = statusMessageService;
    }

    public void save(StatusMessageDTO statusMessageDTO) {
        StatusMessage statusMessage = new StatusMessage();

        try {
            statusMessageDTO.getStatus_message().mappingFromDTO(statusMessage);
            statusMessageService.save(statusMessage);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        return statusMessage;
    }
}
