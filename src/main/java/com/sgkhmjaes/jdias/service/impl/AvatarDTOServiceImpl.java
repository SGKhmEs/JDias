
package com.sgkhmjaes.jdias.service.impl;

import com.sgkhmjaes.jdias.repository.ProfileRepository;
import com.sgkhmjaes.jdias.service.dto.AvatarDTO;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvatarDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(AuthorDTOServiceImpl.class);
    
    private final ProfileRepository profileRepository;

    public AvatarDTOServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
   public AvatarDTO findOne(Long id) {
       log.debug("Request to get Avatar : {}", id);
       AvatarDTO avatarDTO = new AvatarDTO();
        try {
            avatarDTO.mappingToDTO(profileRepository.findOne(id));
        } catch (InvocationTargetException ex) {
            java.util.logging.Logger.getLogger(AvatarDTOServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       return avatarDTO;
   }
   
}
