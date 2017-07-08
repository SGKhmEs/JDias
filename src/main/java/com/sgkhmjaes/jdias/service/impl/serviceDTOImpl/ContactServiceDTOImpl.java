package com.sgkhmjaes.jdias.service.impl.serviceDTOImpl;

import com.sgkhmjaes.jdias.domain.Contact;
import com.sgkhmjaes.jdias.repository.ContactRepository;
import com.sgkhmjaes.jdias.repository.search.ContactSearchRepository;
import com.sgkhmjaes.jdias.service.UserService;
import com.sgkhmjaes.jdias.service.dto.ContactDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by inna on 7/8/17.
 */
public class ContactServiceDTOImpl {
    private final Logger log = LoggerFactory.getLogger(ContactServiceDTOImpl.class);

    private final ContactRepository contactRepository;
    private final UserService userService;

    private final ContactSearchRepository contactSearchRepository;

    public ContactServiceDTOImpl(ContactRepository contactRepository,
                                ContactSearchRepository contactSearchRepository,
                                UserService userService) {
        this.contactRepository = contactRepository;
        this.contactSearchRepository = contactSearchRepository;
        this.userService = userService;
    }

    /**
     * Save a contact (DTO service).
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    public Contact save(ContactDTO contactDTO) {
        log.debug("Request to save Contact DTO: {}", contactDTO);

        Contact contact = new Contact();
        try {
            contactDTO.mappingFromDTO(contact);
        } catch (InvocationTargetException e) {e.printStackTrace();
        }
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return result;
    }

}
