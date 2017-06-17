/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.domain.Profile;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 *
 * @author andrey
 */
public class PersonDTO implements AutoMapping{

    private String relationship;

    private String guid;

    private String id;

    private String name;
    
    @JsonProperty("block")
    private Boolean closedAccount;

    private String is_own_profile;

    @JsonProperty("show_profile_info")
    private String fetchStatus;

    @JsonProperty("profile")
    private ProfileDTO profileDTO;

    private String diaspora_id;

    public PersonDTO() {
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getClosedAccount() {
        return closedAccount;
    }

    public void setClosedAccount(Boolean closedAccount) {
        this.closedAccount = closedAccount;
    }

    public String getIs_own_profile() {
        return is_own_profile;
    }

    public void setIs_own_profile(String is_own_profile) {
        this.is_own_profile = is_own_profile;
    }

    public String getFetchStatus() {
        return fetchStatus;
    }

    public void setFetchStatus(String fetchStatus) {
        this.fetchStatus = fetchStatus;
    }

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }

    public String getDiaspora_id() {
        return diaspora_id;
    }

    public void setDiaspora_id(String diaspora_id) {
        this.diaspora_id = diaspora_id;
    }
    
    
}
