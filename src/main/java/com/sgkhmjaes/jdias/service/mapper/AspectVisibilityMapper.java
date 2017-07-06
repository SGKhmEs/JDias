package com.sgkhmjaes.jdias.service.mapper;

import com.sgkhmjaes.jdias.domain.*;
import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AspectVisibility and its DTO AspectVisibilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AspectVisibilityMapper extends EntityMapper <AspectVisibilityDTO, AspectVisibility> {
    
    
    default AspectVisibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        AspectVisibility aspectVisibility = new AspectVisibility();
        aspectVisibility.setId(id);
        return aspectVisibility;
    }
}
