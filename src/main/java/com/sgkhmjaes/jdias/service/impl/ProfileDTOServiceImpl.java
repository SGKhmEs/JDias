/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Profile;
import com.sgkhmjaes.jdias.repository.ProfileRepository;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import com.sgkhmjaes.jdias.service.dto.ProfileDTO;
import java.lang.reflect.InvocationTargetException;
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
class ProfileDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(ProfileDTOServiceImpl.class);
    
    private final ProfileRepository profileRepository;
    
    private final AvatarDTOServiceImpl avatarDTOServiceImpl;

    public ProfileDTOServiceImpl(ProfileRepository profileRepository, AvatarDTOServiceImpl avatarDTOServiceImpl) {
        this.profileRepository = profileRepository;
        this.avatarDTOServiceImpl = avatarDTOServiceImpl;
    }
    
    public ProfileDTO findOne(Long id) {
        ProfileDTO profileDTO = new ProfileDTO();
        
        Profile profile = profileRepository.findOne(id);
        
        AvatarDTO avatarDTO = avatarDTOServiceImpl.findOne(id);
        
        try {
            profileDTO.mappingToDTO(profile, avatarDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(ProfileDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return profileDTO;
    }
}
