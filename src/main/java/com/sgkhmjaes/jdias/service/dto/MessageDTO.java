
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.ZonedDateTime;

public class MessageDTO implements AutoMapping{
    
    @JsonProperty("id")
    private Long id;
    @JsonProperty("author")
    private AuthorDTO authorDTO; // The diaspora ID of the author of the message.
    @JsonProperty("text")
    private String text; // The message text.
    @JsonProperty("createdAt")
    private ZonedDateTime createdAt; // The create timestamp of the message.

    
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
    
    @Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append ("MessageDTO: {").append("id=").append(id).append(", ").
append("author d t o=").append(authorDTO).append(", ").
append("text=").append(text).append(", ").
append("created at=").append(createdAt).append("}; \n"); 
return sb.toString();
}
    
}
