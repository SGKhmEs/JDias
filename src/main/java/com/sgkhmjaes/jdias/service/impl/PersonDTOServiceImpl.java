/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.service.dto.PersonDTO;
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
class PersonDTOServiceImpl {
    
    private final Logger log = LoggerFactory.getLogger(PersonDTOServiceImpl.class);
    
    private final PersonRepository personRepository;
    
    private final ProfileDTOServiceImpl profileDTOServiceImpl;

    public PersonDTOServiceImpl(PersonRepository personRepository, ProfileDTOServiceImpl profileDTOServiceImpl) {
        this.personRepository = personRepository;
        this.profileDTOServiceImpl = profileDTOServiceImpl;
    }
    
    public PersonDTO findOne(Long id) {
        
        PersonDTO personDTO = new PersonDTO();
        
        Person person = personRepository.findOne(id);
        
        ProfileDTO profileDTO = profileDTOServiceImpl.findOne(id);
        
        try {
            personDTO.mappingToDTO(person, profileDTO);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(PersonDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return personDTO;
        
    }
    
    
}
