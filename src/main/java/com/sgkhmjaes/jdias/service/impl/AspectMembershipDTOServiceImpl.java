/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.repository.AspectMembershipRepository;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.service.dto.AspectDTO;
import com.sgkhmjaes.jdias.service.dto.AspectMembershipDTO;
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
class AspectMembershipDTOServiceImpl {

    private final Logger log = LoggerFactory.getLogger(AspectMembershipDTOServiceImpl.class);

    private final AspectMembershipRepository aspectMembershipRepository;

    private final AspectDTOServiceImpl aspectDTOServiceImpl;

    public AspectMembershipDTOServiceImpl(AspectMembershipRepository aspectMembershipRepository, AspectDTOServiceImpl aspectDTOServiceImpl) {
        this.aspectMembershipRepository = aspectMembershipRepository;
        this.aspectDTOServiceImpl = aspectDTOServiceImpl;
    }

    public AspectMembershipDTO findOne(Long id) {
        AspectMembershipDTO aspectMembershipDTO = new AspectMembershipDTO();
        List<AspectDTO> aspectDTOs = aspectDTOServiceImpl.findAll();
        AspectMembership aspectMembership = aspectMembershipRepository.findOne(id);

        try {
            aspectMembershipDTO.mappingToDTO(aspectMembership, aspectDTOs);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(AspectMembershipDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aspectMembershipDTO;
    }

    public List<AspectMembershipDTO> findAllByContactId(Long contactId) {
        List<AspectMembership> aspectMemberships = aspectMembershipRepository.findAll(); //TODO by user

        List<AspectMembershipDTO> aspectMembershipDTOs = new ArrayList<AspectMembershipDTO>();

        for (AspectMembership aspectMembership : aspectMemberships) {
            AspectMembershipDTO aspectMembershipDTO = new AspectMembershipDTO();

            List<AspectDTO> aspectDTOs = aspectDTOServiceImpl.findAll();

            try {
                aspectMembershipDTO.mappingToDTO(aspectMembership, aspectDTOs);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(AspectMembershipDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            aspectMembershipDTOs.add(aspectMembershipDTO);
        }
        return aspectMembershipDTOs;
    }

}
