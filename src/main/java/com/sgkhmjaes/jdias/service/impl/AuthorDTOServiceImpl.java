/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.service.dto.AuthorDTO;
import com.sgkhmjaes.jdias.service.dto.AutoMappingException;
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
public class AuthorDTOServiceImpl {
    private final PersonRepository personRepository;
    private final AvatarDTOServiceImpl avatarDTOServiceImpl;
    
    private final Logger log = LoggerFactory.getLogger(AuthorDTOServiceImpl.class);

    public AuthorDTOServiceImpl(PersonRepository personRepository, AvatarDTOServiceImpl avatarDTOServiceImpl) {
        this.personRepository = personRepository;
        this.avatarDTOServiceImpl = avatarDTOServiceImpl;
    }


    
    @Transactional(readOnly = true)
    public AuthorDTO findOne(Long id) {
        log.debug("Request to get Author : {}", id);
        AuthorDTO authorDTO = new AuthorDTO();
        try {
            authorDTO.autoMapping(personRepository.findOne(id));
        } catch (AutoMappingException ex) {
            java.util.logging.Logger.getLogger(AuthorDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        authorDTO.setAvatar(avatarDTOServiceImpl.findOne(id));
        return authorDTO;
        
    }

}
