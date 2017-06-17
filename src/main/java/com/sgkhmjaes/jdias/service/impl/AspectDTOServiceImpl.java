/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.service.dto.AspectDTO;
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
class AspectDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(AspectDTOServiceImpl.class);
    
    private final AspectService aspectService;

    public AspectDTOServiceImpl(AspectService aspectService) {
        this.aspectService = aspectService;
    }
    
    public AspectDTO findOne(Long id) {
        
    AspectDTO aspectDTO = new AspectDTO();
    
    Aspect aspect = aspectService.findOne(id);
        
        try {
            aspectDTO.mappingToDTO(aspect);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(AspectDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return aspectDTO;
    
    }
    
    public List<AspectDTO> findAll() {
        List<Aspect> aspects = aspectService.findAll();
        
        List<AspectDTO> aspectDTOs = new ArrayList<AspectDTO>();
        for (Aspect aspect : aspects) {
            AspectDTO aspectDTO = new AspectDTO();
            try {
                aspectDTO.mappingToDTO(aspect);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(AspectDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            aspectDTOs.add(aspectDTO);
        }
        
        return aspectDTOs;
    }
    
}
