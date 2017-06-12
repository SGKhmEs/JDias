package com.sgkhmjaes.jdias.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.LocalDate;

public class CommentDTO implements AutoMapping {

    @JsonProperty("guid")
    private String guid;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("author")
    private AuthorDTO authorDTO;
    @JsonProperty("text")
    private String text;
    @JsonProperty("createdAt")
    private LocalDate createdAt;

    public CommentDTO() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comment guid: ").append(guid).append("\r\n").
                append("Comment id: ").append(id).append("\r\n").
                append("Comment author: ").append(authorDTO).append("\r\n").
                append("Comment text: ").append(text).append("\r\n").
                append("Comment created at: ").append(createdAt).append("\r\n");
        return sb.toString();
    }

}
