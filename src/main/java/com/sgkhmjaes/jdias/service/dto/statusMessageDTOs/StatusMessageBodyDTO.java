package com.sgkhmjaes.jdias.service.dto.statusMessageDTOs;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;

/**
 * Created by inna on 10.06.17.
 */
public class StatusMessageBodyDTO implements AutoMapping {
    private String text;

    public StatusMessageBodyDTO(){}

    public StatusMessageBodyDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
