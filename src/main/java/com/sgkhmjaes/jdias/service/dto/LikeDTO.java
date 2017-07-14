package com.sgkhmjaes.jdias.service.dto;

import com.sgkhmjaes.jdias.service.mapper.AutoMapping;
import java.time.LocalDate;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonProperty;

public class LikeDTO implements AutoMapping {

    private Long id;
    private Long post_id;
    private String guid;
    private LocalDate createdAt;
    @JsonProperty("author")
    private AuthorDTO authorDTO;

    public LikeDTO() {
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
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

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

}
