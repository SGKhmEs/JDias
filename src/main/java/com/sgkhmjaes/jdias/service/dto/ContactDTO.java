/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.util.List;

/**
 *
 * @author andrey
 */
public class ContactDTO implements AutoMapping{

    private Long id;

    @JsonProperty("person")
    private PersonDTO personDTO;
    
    @JsonProperty("aspect_memberships")
    private List<AspectMembershipDTO> aspectMembershipsDTO;

//    private String person_id;

    public ContactDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    public List<AspectMembershipDTO> getAspectMembershipsDTO() {
        return aspectMembershipsDTO;
    }

    public void setAspectMembershipsDTO(List<AspectMembershipDTO> aspectMembershipsDTO) {
        this.aspectMembershipsDTO = aspectMembershipsDTO;
    }
    
    

    
}
