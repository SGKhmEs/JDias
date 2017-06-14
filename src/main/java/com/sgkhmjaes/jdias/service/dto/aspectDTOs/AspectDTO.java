package com.sgkhmjaes.jdias.service.dto.aspectDTOs;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class AspectDTO implements AutoMapping {
    private String name;
    private Boolean contactVisible;
    private Boolean chatEnabled;

    public AspectDTO(){}

    public AspectDTO(String name, Boolean contactVisible, Boolean chatEnabled) {
        this.name = name;
        this.contactVisible = contactVisible;
        this.chatEnabled = chatEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getContactVisible() {
        return contactVisible;
    }

    public void setContactVisible(Boolean contactVisible) {
        this.contactVisible = contactVisible;
    }

    public Boolean getChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(Boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }
}
