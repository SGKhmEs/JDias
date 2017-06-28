
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.ZonedDateTime;

public class MessageDTO implements AutoMapping{
    
    private Long id;
    private String text; // The message text.
    private ZonedDateTime createdAt; // The create timestamp of the message.
    @JsonProperty("author")
    private AuthorDTO authorDTO; // The diaspora ID of the author of the message.
    
    public MessageDTO (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    
    
}
