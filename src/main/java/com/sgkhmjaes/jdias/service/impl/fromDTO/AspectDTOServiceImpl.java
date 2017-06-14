package com.sgkhmjaes.jdias.service.impl.fromDTO;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.service.AspectService;
import com.sgkhmjaes.jdias.service.dto.aspectDTOs.AspectDTO;
import com.sgkhmjaes.jdias.service.dto.aspectDTOs.AspectListDTO;
import com.sgkhmjaes.jdias.service.impl.AspectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by inna on 10.06.17.
 */
@Service
@Transactional
public class AspectDTOServiceImpl {
    private final Logger log = LoggerFactory.getLogger(AspectServiceImpl.class);

    private final AspectService aspectService;

    private final AspectRepository aspectRepository;

    public AspectDTOServiceImpl(AspectService aspectService, AspectRepository aspectRepository) {
        this.aspectService = aspectService;
        this.aspectRepository = aspectRepository;
    }

    public void save(AspectDTO aspectDTO) {
        Aspect aspect = new Aspect();

        try {
            aspectDTO.mappingFromDTO(aspect);
            aspectRepository.save(aspect);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}



