package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by inna on 02.06.17.
 */

public class AuthorDTO implements AutoMapping {
    
    @JsonProperty("diaspora_id")
    private String personDiasporaId;
    @JsonProperty("guid")
    private String personGuid;
    @JsonProperty("id")
    private long personId;
    private AvatarDTO avatar;

    public AuthorDTO(){}

    public String getPersonDiasporaId() {
        return personDiasporaId;
    }

    public void setPersonDiasporaId(String personDiasporaId) {
        this.personDiasporaId = personDiasporaId;
    }

    
    
    public String getPersonGuid() {
        return personGuid;
    }

    public void setPersonGuid(String personGuid) {
        this.personGuid = personGuid;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public AvatarDTO getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarDTO avatar) {
        this.avatar = avatar;
    }
    
    @Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append("AuthorDTO: {").append("person guid=").append(personGuid).
append(", person id=").append(personId).
append(", ").append(avatar).append("}");
return sb.toString();
}

}
