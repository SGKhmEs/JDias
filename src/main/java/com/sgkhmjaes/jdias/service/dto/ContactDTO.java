/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.domain.Person;
import java.util.List;

/**
 *
 * @author andrey
 */
public class ContactDTO {

    private Long id;

    private Person person;
    
    @JsonProperty("aspect_memberships")
    private List<AspectMembership> aspectMemberships;

//    private String person_id;

    public ContactDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<AspectMembership> getAspectMemberships() {
        return aspectMemberships;
    }

    public void setAspectMemberships(List<AspectMembership> aspectMemberships) {
        this.aspectMemberships = aspectMemberships;
    }
    
    
}
