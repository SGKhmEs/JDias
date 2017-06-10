/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.LocalDate;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author andrey
 */
public class LikeDTO implements AutoMapping{
    private Long id;
    private String guid;
    private LocalDate createdAt;
    @JsonProperty("author")
    private AuthorDTO authorDTO;

    public LikeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public AuthorDTO getAutorDTO() {
        return authorDTO;
    }

    public void setAutorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }
    
    
}
