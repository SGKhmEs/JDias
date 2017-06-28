
package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConversationDTO implements AutoMapping {
    
    private Long id;
    private String author; // The diaspora ID of the author of the conversation.
    private String subject; // The subject of the conversation
    private ZonedDateTime updatedAt; // The last message timestamp of the conversation.
    @JsonProperty("message")
    private List <MessageDTO> messagesDTO = new ArrayList <>(); // All* message of this conversation
    @JsonProperty("author")
    private Set <AuthorDTO> authorDTO = new HashSet <> ();
    
    public ConversationDTO (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void addMessages(MessageDTO messageDTO) {
        this.messagesDTO.add(messageDTO);
        //messageDTO.setConversationDTO(this);
        //return this;
    }
    
    public void addAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO.add(authorDTO);
        //messageDTO.setConversationDTO(this);
        //return this;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<MessageDTO> getMessagesDTO() {
        return messagesDTO;
    }

    public void setMessageDTO(List<MessageDTO> messagesDTO) {
        this.messagesDTO = messagesDTO;
    }

    public void setAuthorDTO(Set<AuthorDTO> authorDTO) {
        this.authorDTO = authorDTO;
    }

    public Set<AuthorDTO> getAuthorDTO() {
        return authorDTO;
    }
    
    
        
}
