package com.sgkhmjaes.jdias.service.dto;

/**
 * Created by inna on 02.06.17.
 */

public class AuthorDTO {
    private String personGuid;
    private long personId;
    private AvatarDTO avatar;

    public AuthorDTO(){}

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

    
}
