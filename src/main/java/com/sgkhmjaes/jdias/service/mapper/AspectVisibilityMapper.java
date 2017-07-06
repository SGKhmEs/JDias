package com.sgkhmjaes.jdias.service.mapper;

import com.sgkhmjaes.jdias.domain.*;
import com.sgkhmjaes.jdias.service.dto.AspectvisibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Aspectvisibility and its DTO AspectvisibilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AspectvisibilityMapper extends EntityMapper <AspectvisibilityDTO, Aspectvisibility> {
    
    
    default Aspectvisibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aspectvisibility aspectvisibility = new Aspectvisibility();
        aspectvisibility.setId(id);
        return aspectvisibility;
    }
}
