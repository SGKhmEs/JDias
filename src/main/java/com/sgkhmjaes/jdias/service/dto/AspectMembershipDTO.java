/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author andrey
 */
public class AspectMembershipDTO {
    
    private String id;

    @JsonProperty("aspect")
    private AspectDTO aspectDTO;

    public AspectMembershipDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AspectDTO getAspectDTO() {
        return aspectDTO;
    }

    public void setAspectDTO(AspectDTO aspectDTO) {
        this.aspectDTO = aspectDTO;
    }
    
    
    
}
