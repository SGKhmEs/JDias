/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.service.ContactService;
import com.sgkhmjaes.jdias.service.dto.AspectMembershipDTO;
import com.sgkhmjaes.jdias.service.dto.ContactDTO;
import com.sgkhmjaes.jdias.service.dto.PersonDTO;
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
public class ContactDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(ContactDTOServiceImpl.class);
    
    private final ContactService contactService;
    
    private final ContactRepository contactRepository;
    
    private final PersonDTOServiceImpl personDTOServiceImpl;
    
    private final AspectMembershipDTOServiceImpl aspectMembershipDTOServiceImpl;

    public ContactDTOServiceImpl(ContactService contactService, ContactRepository contactRepository, PersonDTOServiceImpl personDTOServiceImpl, AspectMembershipDTOServiceImpl aspectMembershipDTOServiceImpl) {
        this.contactService = contactService;
        this.contactRepository = contactRepository;
        this.personDTOServiceImpl = personDTOServiceImpl;
        this.aspectMembershipDTOServiceImpl = aspectMembershipDTOServiceImpl;
    }




    
    public ContactDTO findOneById(Long id) {
        log.debug("Request to get Contact by ID: {}", id);
        
        ContactDTO contactDTO = new ContactDTO();
        Contact contact = contactRepository.findOne(id);
        PersonDTO personDTO = personDTOServiceImpl.findOne(id);
        List<AspectMembershipDTO> aspectMembershipDTOs = aspectMembershipDTOServiceImpl.findAllByContactId(id);
        
               
        try {
            contactDTO.mappingToDTO(contact, personDTO, aspectMembershipDTOs);
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(ContactDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contactDTO;
    }
    
    public List<ContactDTO> findAllByLoggedUser(){
//        List<Contact> contacts = contactService.findAll();
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDTO> contactDTOs = new ArrayList<ContactDTO>();
        
        for (Contact contact : contacts) {
            PersonDTO personDTO = personDTOServiceImpl.findOne(contact.getPerson().getId());
            
            ContactDTO contactDTO = new ContactDTO();
            
            List<AspectMembershipDTO> aspectMembershipDTOs = aspectMembershipDTOServiceImpl.findAllByContactId(contact.getId());
            
            try {
                contactDTO.mappingToDTO(contact, personDTO, aspectMembershipDTOs);
            } catch (InvocationTargetException ex) {
                java.util.logging.Logger.getLogger(ContactDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return contactDTOs;
    }
    
    
}
