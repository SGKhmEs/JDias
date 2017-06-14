package com.sgkhmjaes.jdias.service.dto.aspectDTOs;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class AspectResponseDTO implements AutoMapping{
    private long id;
    private String name;
    private int order;
    private boolean contacts_visible;
    private boolean chat_enabled;

    public AspectResponseDTO(){}

    public AspectResponseDTO(long id, String name, int order, boolean contacts_visible, boolean chat_enabled) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.contacts_visible = contacts_visible;
        this.chat_enabled = chat_enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isContacts_visible() {
        return contacts_visible;
    }

    public void setContacts_visible(boolean contacts_visible) {
        this.contacts_visible = contacts_visible;
    }

    public boolean isChat_enabled() {
        return chat_enabled;
    }

    public void setChat_enabled(boolean chat_enabled) {
        this.chat_enabled = chat_enabled;
    }
}
