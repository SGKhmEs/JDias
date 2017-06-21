
package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonProperty;

public class ConversationDTO implements AutoMapping {
    
    /*
    <conversation>
  <author>alice@example.org</author>
  <guid>9b1376a029eb013487753131731751e9</guid>
  <subject>this is a very informative subject</subject>
  <created_at>2016-07-11T23:17:48Z</created_at>
  <participants>alice@example.org;bob@example.com</participants>
  <message>
    <guid>5cc5692029eb013487753131731751e9</guid>
    <text>this is a very informative text</text>
    <created_at>2016-07-11T23:17:48Z</created_at>
    <author>alice@example.org</author>
    <conversation_guid>9b1376a029eb013487753131731751e9</conversation_guid>
  </message>
</conversation>
    */
    
    //private Long id;
    private String author; // The diaspora ID of the author of the conversation.
    private String guid; // The GUID of the conversation
    private String subject; // The subject of the conversation
    private LocalDate createdAt; // The create timestamp of the conversation.
    private LocalDate updatedAt; // The last message timestamp of the conversation.
    private String message; // The first message in the conversation, needs to be the same author.
    //@JsonProperty("messageDTO")
    private List <Message> allMessage; // All* message of this conversation 
    @JsonProperty("autorDTO")
    private List <AuthorDTO> authorDTO;
    
    public ConversationDTO (){}
    
    public void setAllMessage(List<Message> allMessage) {
        this.allMessage = allMessage;
    }

    public List<Message> getAllMessage() {
        return allMessage;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
        
    public List<AuthorDTO> getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(List<AuthorDTO> authorDTO) {
        this.authorDTO = authorDTO;
    }
        
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

    
}
