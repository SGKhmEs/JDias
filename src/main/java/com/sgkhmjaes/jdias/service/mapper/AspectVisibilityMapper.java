package com.sgkhmjaes.jdias.service.mapper;

import com.sgkhmjaes.jdias.domain.*;
import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AspectVisibility and its DTO AspectVisibilityDTO.
 */
@Mapper(componentModel = "spring", uses = {AspectMapper.class, PostMapper.class, })
public interface AspectVisibilityMapper extends EntityMapper <AspectVisibilityDTO, AspectVisibility> {

    @Mapping(source = "aspect.id", target = "aspectId")

    @Mapping(source = "post.id", target = "postId")
    AspectVisibilityDTO toDto(AspectVisibility aspectVisibility); 

    @Mapping(source = "aspectId", target = "aspect")

    @Mapping(source = "postId", target = "post")
    AspectVisibility toEntity(AspectVisibilityDTO aspectVisibilityDTO); 
    default AspectVisibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        AspectVisibility aspectVisibility = new AspectVisibility();
        aspectVisibility.setId(id);
        return aspectVisibility;
    }
}
