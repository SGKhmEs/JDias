
package com.sgkhmjaes.jdias.service.dto;

import java.time.LocalDate;

public class MessageDTO {
    
    /*
    <message>
  <author>alice@example.org</author>
  <guid>5cc5692029eb013487753131731751e9</guid>
  <conversation_guid>9b1376a029eb013487753131731751e9</conversation_guid>
  <text>this is a very informative text</text>
  <created_at>2016-07-11T23:17:48Z</created_at>
</message>
    */
    
    //private Long id;
    private String author; // The diaspora ID of the author of the message.
    private String guid; // The GUID of the message.
    private String conversationGuid; // The GUID of the Conversation.
    private String text; // The message text.
    private LocalDate createdAt; // The create timestamp of the message.
    //private Conversation conversation;
    
    public MessageDTO (){}

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

    public String getConversationGuid() {
        return conversationGuid;
    }

    public void setConversationGuid(String conversationGuid) {
        this.conversationGuid = conversationGuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    
    
}
